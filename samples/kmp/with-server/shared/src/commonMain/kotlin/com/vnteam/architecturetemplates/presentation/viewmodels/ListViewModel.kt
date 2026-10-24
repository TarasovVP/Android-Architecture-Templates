package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.usecase.ListUseCase
import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val listUseCase: ListUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow(ListViewState())
    val state: StateFlow<ListViewState> = _state.asStateFlow()

    fun processIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.ClearDemoObjects -> clearDemoObjects()
            is ListIntent.LoadDemoObjects -> getDemoObjectsFromApi(intent.isInit)
            is ListIntent.DeleteDemoObject -> deleteDemoObjectById(intent.id)
        }
    }

    private fun clearDemoObjects() {
        viewModelScope.launch(exceptionHandler) {
            listUseCase.clearDemoObjects()
            getDemoObjectsFromApi(true)
        }
    }

    private fun getDemoObjectsFromApi(isInit: Boolean) {
        if (isInit) showProgress(true)
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
                _state.value = state.value.copy(demoObjectUIs = demoObjects)
                showProgress(false)
            }
        }
    }

    private fun deleteDemoObjectById(demoObjectId: String) {
        showProgress(true)
        _state.value = state.value.copy(successResult = false)
        viewModelScope.launch(exceptionHandler) {
            listUseCase.deleteDemoObjectById(demoObjectId).collect {
                _state.value = state.value.copy(successResult = true)
                showMessage("Successfully deleted", false)
                showProgress(false)
            }
        }
    }
}