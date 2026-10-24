package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.vnteam.architecturetemplates.data.APP_LANG_EN
import com.vnteam.architecturetemplates.data.APP_LANG_UK
import com.vnteam.architecturetemplates.domain.usecase.IsDarkThemeUseCase
import com.vnteam.architecturetemplates.domain.usecase.LanguageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AppViewModel(
    private val isDarkThemeUseCaseImpl: IsDarkThemeUseCase,
    private val languageUseCaseImpl: LanguageUseCase,
) : BaseViewModel() {
    private val _isDarkTheme = MutableStateFlow<Boolean?>(null)
    val isDarkTheme: StateFlow<Boolean?> = _isDarkTheme.asStateFlow()

    private val _language = MutableStateFlow<String?>(null)
    val language: StateFlow<String?> = _language.asStateFlow()

    init {
        getIsDarkTheme()
        getLanguage()
    }

    fun getIsDarkTheme() {
        viewModelScope.launch(exceptionHandler) {
            isDarkThemeUseCaseImpl.get().collect {
                _isDarkTheme.value = it
            }
        }
    }

    fun setIsDarkTheme(isDarkTheme: Boolean) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            isDarkThemeUseCaseImpl.set(isDarkTheme)
            showProgress(false)
        }
    }

    fun getLanguage() {
        viewModelScope.launch(exceptionHandler) {
            languageUseCaseImpl.get().collect {
                _language.value = it
            }
        }
    }

    fun setLanguage(language: String) {
        showProgress(true)
        viewModelScope.launch(exceptionHandler) {
            languageUseCaseImpl.set(language)
            showProgress(false)
        }
    }

    fun getNewLanguage(): String {
        return if (language.value == APP_LANG_EN) {
            APP_LANG_UK
        } else {
            APP_LANG_EN
        }
    }
}
