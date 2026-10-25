package com.vnteam.architecturetemplates.data.mappers

import com.vnteam.architecturetemplates.data.network.responses.OwnerResponse
import com.vnteam.architecturetemplates.domain.mappers.BaseMapper
import com.vnteam.architecturetemplates.domain.models.Owner

interface OwnerResponseMapper : BaseMapper<Owner, OwnerResponse>
