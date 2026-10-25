package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.usecase.ListUseCase
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.states.InfoMessageState
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val listUseCase: ListUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper
) : ViewModel() {

    private val _state = MutableStateFlow(ListViewState())
    val state: StateFlow<ListViewState> = _state.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        _state.value = state.value.copy(isLoading = false, infoMessage = mutableStateOf( InfoMessageState(message = exception.message.orEmpty(), isError = true)))
    }

    fun processIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.ClearDemoObjects -> clearDemoObjects()
            is ListIntent.LoadDemoObjects -> getDemoObjectsFromApi()
        }
    }

    private fun clearDemoObjects() {
        viewModelScope.launch(exceptionHandler) {
            listUseCase.clearDemoObjects()
        }
    }

    private fun getDemoObjectsFromApi() {
        _state.value = state.value.copy(isLoading = true)
        viewModelScope.launch(exceptionHandler) {
            listUseCase.getDemoObjectsFromApi().collect { demoObjects ->
                insertDemoObjectsToDB(demoObjects)
            }
        }
    }

    private fun insertDemoObjectsToDB(demoObjects: List<DemoObject>?) {
        viewModelScope.launch(exceptionHandler) {
            demoObjects?.let {
                listUseCase.insertDemoObjectsToDB(it).collect {
                    getDemoObjectsFromDB()
                }
            }
        }
    }

    private fun getDemoObjectsFromDB() {
        viewModelScope.launch(exceptionHandler) {
            listUseCase.getDemoObjectsFromDB().collect {
                val demoObjects = demoObjectUIMapper.mapToImplModelList(it)
                _state.value = state.value.copy(demoObject = demoObjects, isLoading = false)
            }
        }
    }
}