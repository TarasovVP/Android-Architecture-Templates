package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.usecase.DetailsUseCase
import com.vnteam.architecturetemplates.presentation.intents.DetailsIntent
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.presentation.states.DetailsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val detailsUseCase: DetailsUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper
) : BaseViewModel() {

    private val _state = MutableStateFlow(DetailsViewState())
    val state: StateFlow<DetailsViewState> = _state.asStateFlow()

    fun processIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadDemoObject -> getDemoObjectById(intent.demoObjectId, intent.isUpdated)
        }
    }

    private fun getDemoObjectById(demoObjectId: String?, isUpdated: Boolean) {
        showProgress(true)
        if (isUpdated) {
            _state.value = _state.value.copy(demoObjectUI = null)
        }
        viewModelScope.launch(exceptionHandler) {
            detailsUseCase.getDemoObjectById(demoObjectId.orEmpty()).collect { demoObject ->
                showProgress(false)
                _state.value = _state.value.copy(demoObjectUI = demoObject?.let { demoObjectUIMapper.mapToImplModel(it) })
            }
        }
    }
}