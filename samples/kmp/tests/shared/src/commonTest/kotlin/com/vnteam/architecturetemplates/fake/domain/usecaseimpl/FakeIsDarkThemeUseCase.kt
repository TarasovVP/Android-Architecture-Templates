package com.vnteam.architecturetemplates.fake.domain.usecaseimpl

import com.vnteam.architecturetemplates.domain.usecase.IsDarkThemeUseCase
import kotlinx.coroutines.flow.flow

class FakeIsDarkThemeUseCase : IsDarkThemeUseCase {
    private var darkTheme: Boolean? = false

    override suspend fun set(params: Boolean) {
        darkTheme = params
    }

    override fun get() = flow { emit(darkTheme) }
}