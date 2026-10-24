package com.vnteam.architecturetemplates.details

import com.vnteam.architecturetemplates.presentation.mappers.ForkUIMapper
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val forkRepository: DBRepository,
    private val forkUIMapper: ForkUIMapper,
) {
    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private val _state = MutableStateFlow(DetailsViewState())
    val state: StateFlow<DetailsViewState> = _state.asStateFlow()

    fun processIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadFork -> getForkById(intent.forkId)
        }
    }

    fun getForkById(forkId: Long?) {
        viewModelScope.launch {
            forkRepository.getForkById(forkId ?: 0)
                .onStart {
                    _state.value = _state.value.copy(isLoading = true)
                }
                .catch { exception ->
                    _state.value = _state.value.copy(error = exception.message, isLoading = false)
                }
                .collect { fork ->
                    _state.value = _state.value.copy(fork = fork?.let { forkUIMapper.mapToImplModel(it) }, isLoading = false)
                }
        }
    }
}