package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface InsertDemoObjectsUseCase : UseCase<List<DemoObject>, Unit>