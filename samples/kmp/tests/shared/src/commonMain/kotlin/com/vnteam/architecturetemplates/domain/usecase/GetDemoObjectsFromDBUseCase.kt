package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject
import kotlinx.coroutines.flow.Flow

interface GetDemoObjectsFromDBUseCase : UseCase<Nothing?, Flow<List<DemoObject>?>>