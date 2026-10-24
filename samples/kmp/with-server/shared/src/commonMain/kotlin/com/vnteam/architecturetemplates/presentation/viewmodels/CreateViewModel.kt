package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.models.DemoObject
import com.vnteam.architecturetemplates.domain.usecase.CreateUseCase
import com.vnteam.architecturetemplates.presentation.intents.CreateIntent
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.presentation.states.CreateViewState
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateViewModel(
    private val createUseCase: CreateUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreateViewState())
    val state: StateFlow<CreateViewState> = _state.asStateFlow()

    fun processIntent(intent: CreateIntent) {
        when (intent) {
            is CreateIntent.LoadDemoObject -> getDemoObjectById(intent.demoObjectId)
            is CreateIntent.CreateDemoObject -> createDemoObject(state.value.demoObject.value)
        }
    }

    private fun getDemoObjectById(demoObjectId: String?) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            createUseCase.getDemoObjectById(demoObjectId.orEmpty()).collect { demoObject ->
                showProgress(false)
                _state.value = _state.value.copy(demoObject = mutableStateOf( demoObject?.let { demoObjectUIMapper.mapToImplModel(it) }))
            }
        }
    }

    private fun createDemoObject(demoObject: DemoObjectUI?) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            createUseCase.createDemoObject(demoObject?.let { demoObjectUIMapper.mapFromImplModel(it) } ?: DemoObject()).collect {
                insertDemoObjectToDB(demoObject)
            }
        }
    }

    private fun insertDemoObjectToDB(demoObject: DemoObjectUI?) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            createUseCase.insertDemoObjectToDB(demoObject?.let { demoObjectUIMapper.mapFromImplModel(it) } ?: DemoObject()).collect { demoObject ->
                showProgress(false)
                showMessage("Successfully created", false)
                _state.value = state.value.copy(successResult = true)
            }
        }
    }
}