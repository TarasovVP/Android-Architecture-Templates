package com.vnteam.architecturetemplates.data.mappers

import com.vnteam.architecturetemplates.data.network.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.domain.mappers.BaseMapper
import com.vnteam.architecturetemplates.domain.models.DemoObject

interface DemoObjectResponseMapper : BaseMapper<DemoObject, DemoObjectResponse>
