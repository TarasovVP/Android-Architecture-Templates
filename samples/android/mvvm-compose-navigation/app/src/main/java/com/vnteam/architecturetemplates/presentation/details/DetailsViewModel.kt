package com.vnteam.architecturetemplates.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val demoObjectRepository: DBRepository,
    private val demoObjectUIMapper: DemoObjectUIMapper,
): ViewModel() {

    private val _progressVisibilityFlow = MutableStateFlow(false)
    val progressVisibilityFlow: StateFlow<Boolean> = _progressVisibilityFlow

    private val _errorFlow = MutableStateFlow<String?>(null)
    val errorFlow: StateFlow<String?> = _errorFlow

    private val _demoObjectFlow = MutableStateFlow<DemoObjectUI?>(null)
    val demoObjectFlow: StateFlow<DemoObjectUI?> = _demoObjectFlow

    fun getDemoObjectById(demoObjectId: Long?) {
        viewModelScope.launch {
            demoObjectRepository.getDemoObjectById(demoObjectId ?: 0)
                .onStart {
                    _progressVisibilityFlow.value = true
                }
                .catch { exception ->
                    _progressVisibilityFlow.value = false
                    _errorFlow.value = exception.localizedMessage
                }
                .collect { demoObject ->
                    _demoObjectFlow.value = demoObject?.let { demoObjectUIMapper.mapToImplModel(it) }
                    _progressVisibilityFlow.value = false
                }
        }
    }
}