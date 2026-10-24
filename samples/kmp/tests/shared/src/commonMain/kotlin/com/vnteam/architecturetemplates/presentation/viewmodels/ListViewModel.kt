package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.domain.usecase.execute

import com.vnteam.architecturetemplates.presentation.intents.ListIntent
import com.vnteam.architecturetemplates.presentation.states.ListViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val clearDemoObjectUseCase: ClearDemoObjectUseCase,
    private val getDemoObjectsFromDBUseCase: GetDemoObjectsFromDBUseCase,
    private val getDemoObjectsFromApiUseCase: GetDemoObjectsFromApiUseCase,
    private val insertDemoObjectsUseCase: InsertDemoObjectsUseCase,
    private val deleteDemoObjectUseCase: DeleteDemoObjectUseCase,
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
            clearDemoObjectUseCase.execute()
            _state.value = state.value.copy(clearSuccessResult = true)
        }
    }

    private fun getDemoObjectsFromApi(isInit: Boolean) {
        if (isInit) showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            val demoObjects = getDemoObjectsFromApiUseCase.execute()
            insertDemoObjectsToDB(demoObjects)
        }
    }

    private fun insertDemoObjectsToDB(demoObjects: List<DemoObject>?) {
        viewModelScope.launch(exceptionHandler) {
            demoObjects?.let {
                insertDemoObjectsUseCase.execute(it)
                getDemoObjectsFromDB()
            }
        }
    }

    private fun getDemoObjectsFromDB() {
        viewModelScope.launch(exceptionHandler) {
            getDemoObjectsFromDBUseCase.execute().collect {
                val demoObjects = demoObjectUIMapper.mapToImplModelList(it.orEmpty())
                _state.value = state.value.copy(demoObjectUIs = demoObjects)
                showProgress(false)
            }
        }
    }

    private fun deleteDemoObjectById(demoObjectId: String) {
        showProgress(true)
        _state.value = state.value.copy(successResult = false)
        viewModelScope.launch(exceptionHandler) {
            deleteDemoObjectUseCase.execute(demoObjectId)
            _state.value = state.value.copy(successResult = true)
            showMessage("Successfully deleted", false)
            showProgress(false)
        }
    }
}