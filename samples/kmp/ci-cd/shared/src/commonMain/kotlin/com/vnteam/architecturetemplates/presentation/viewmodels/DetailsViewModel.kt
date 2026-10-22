package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.presentation.intents.DetailsIntent
import com.vnteam.architecturetemplates.presentation.states.DetailsViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val getDemoObjectUseCase: GetDemoObjectUseCase,
    private val demoObjectUIMapper: DemoObjectUIMapper,
) : BaseViewModel() {
    private val _state = MutableStateFlow(DetailsViewState())
    val state = _state.asStateFlow()

    fun processIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadDemoObject -> {
                getDemoObjectById(
                    intent.demoObjectId,
                    intent.isUpdated,
                )
            }
        }
    }

    private fun getDemoObjectById(
        demoObjectId: String?,
        isUpdated: Boolean,
    ) {
        showProgress(true)
        if (isUpdated) {
            _state.value = _state.value.copy(demoObjectUI = null)
        }
        viewModelScope.launch(exceptionHandler) {
            val demoObject = getDemoObjectUseCase.execute(demoObjectId.orEmpty())
            showProgress(false)
            _state.value =
                _state.value.copy(
                    demoObjectUI =
                        demoObject?.let {
                            demoObjectUIMapper.mapToImplModel(it)
                        },
                )
        }
    }
}
