package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.domain.usecase.AppUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val appUseCase: AppUseCase
) : BaseViewModel() {

    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    private val _language = MutableStateFlow<String?>(null)
    val language: StateFlow<String?> = _language.asStateFlow()

    init {
        getIsDarkTheme()
        getLanguage()
    }

    private fun getIsDarkTheme() {
        viewModelScope.launch(exceptionHandler) {
            appUseCase.getIsDarkTheme().collect {
                _isDarkTheme.value = it
            }
        }
    }

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            showProgress(false)
            appUseCase.setIsDarkTheme(isDarkTheme)
        }
    }

    private fun getLanguage() {
        viewModelScope.launch(exceptionHandler) {
            appUseCase.getLanguage().collect {
                _language.value = it
            }
        }
    }

    fun setLanguage(language: String) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            showProgress(false)
            appUseCase.setLanguage(language)
        }
    }
}