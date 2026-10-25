package com.vnteam.architecturetemplates.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.usecase.DemoObjectUseCase
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

    private val _state = MutableStateFlow(ListViewState())
    val state: StateFlow<ListViewState> = _state.asStateFlow()

    fun processIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.LoadDemoObjects -> getDemoObjectsFromApi()
            else -> Unit
        }
    }

    private fun getDemoObjectsFromApi() {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _state.value = state.value.copy(isLoading = false, error = exception.message)
        }) {
            _state.value = state.value.copy(isLoading = true)
            val demoObjects = demoObjectUseCase.getDemoObjectsFromApi()
            insertDemoObjectsToDB(demoObjects)
            getDemoObjectsFromDB()
        }
    }

    private fun insertDemoObjectsToDB(demoObjects: Flow<List<DemoObject>?>) {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _state.value = state.value.copy(isLoading = false, error = exception.message)
        }) {
            demoObjects.collect {
                it ?: return@collect
                demoObjectUseCase.insertDemoObjectsToDB(it)
            }
        }
    }

    private fun getDemoObjectsFromDB() {
        viewModelScope.launch(CoroutineExceptionHandler { _, exception ->
            _state.value = state.value.copy(isLoading = false, error = exception.message)
        }) {
            demoObjectUseCase.getDemoObjectsFromDB().collect {
                val demoObjects = demoObjectUIMapper.mapToImplModelList(it)
                _state.value = state.value.copy(demoObjects = demoObjects, isLoading = false)
            }
        }
    }
}