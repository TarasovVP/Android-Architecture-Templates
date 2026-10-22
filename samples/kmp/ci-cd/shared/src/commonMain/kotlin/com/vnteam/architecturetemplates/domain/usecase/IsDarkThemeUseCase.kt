package com.vnteam.architecturetemplates.domain.usecase

import kotlinx.coroutines.flow.Flow

interface IsDarkThemeUseCase : DataUseCase<Boolean, Flow<Boolean?>>
