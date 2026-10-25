package com.vnteam.architecturetemplates.domain.usecase

import kotlinx.coroutines.flow.Flow

interface LanguageUseCase : DataUseCase<String, Flow<String?>>
