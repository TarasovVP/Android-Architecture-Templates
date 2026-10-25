package com.vnteam.architecturetemplates.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.DemoObjectUseCase
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val demoObjectUseCase: DemoObjectUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper,
) : ViewModel() {

    private val _progressVisibilityFlow = MutableStateFlow(false)
    val progressVisibilityFlow: StateFlow<Boolean> = _progressVisibilityFlow.asStateFlow()

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: StateFlow<String?> = _errorFlow.asStateFlow()

    private val _demoObjectsFromDBFlow = MutableStateFlow<List<DemoObjectUI>>(emptyList())
    val demoObjectsFromDBFlow: StateFlow<List<DemoObjectUI>> = _demoObjectsFromDBFlow.asStateFlow()

    fun getDemoObjectsFromApi() {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _progressVisibilityFlow.value = false
            _errorFlow.value = exception.localizedMessage
        }) {
            _progressVisibilityFlow.value = true
            val demoObjects = demoObjectUseCase.getDemoObjectsFromApi()
            insertDemoObjectsToDB(demoObjects)
            getDemoObjectsFromDB()
        }
    }

    private fun insertDemoObjectsToDB(demoObjects: Flow<List<DemoObject>?>) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _progressVisibilityFlow.value = false
            _errorFlow.value = exception.localizedMessage
        }) {
            demoObjects.collect {
                it ?: return@collect
                demoObjectUseCase.insertDemoObjectsToDB(it)
            }
        }
    }

    private fun getDemoObjectsFromDB() {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _progressVisibilityFlow.value = false
            _errorFlow.value = exception.localizedMessage
        }) {
            demoObjectUseCase.getDemoObjectsFromDB().collect {
                val demoObjectUIS = demoObjectUIMapper.mapToImplModelList(it)
                _demoObjectsFromDBFlow.value = demoObjectUIS
                _progressVisibilityFlow.value = false
            }
        }
    }
}