package com.vnteam.architecturetemplates.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.vnteam.architecturetemplates.presentation.states.screen.ScreenState
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    val screenState = mutableStateOf( ScreenState() )

    protected val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        showProgress(false)
        showMessage(exception.message.orEmpty(), true)
    }

    protected fun showMessage(message: String, isError: Boolean = false) {
        screenState.value = screenState.value.copy(appMessageState = screenState.value.appMessageState.copy(messageVisible = true, messageText = message, isMessageError = isError))
    }

    protected fun showProgress(isShow: Boolean) {
        screenState.value = screenState.value.copy(isProgressVisible = isShow)
    }
}