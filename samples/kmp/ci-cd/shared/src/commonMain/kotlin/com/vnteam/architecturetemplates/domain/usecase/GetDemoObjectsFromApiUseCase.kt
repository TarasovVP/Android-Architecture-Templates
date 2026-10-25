package com.vnteam.architecturetemplates.domain.usecase

import com.vnteam.architecturetemplates.domain.models.DemoObject

interface GetDemoObjectsFromApiUseCase : UseCase<Nothing?, List<DemoObject>>
