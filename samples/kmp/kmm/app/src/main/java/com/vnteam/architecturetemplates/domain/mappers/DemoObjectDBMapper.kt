package com.vnteam.architecturetemplates.domain.mappers

import com.vnteam.architecturetemplates.DemoObjectWithOwner
import com.vnteam.architecturetemplates.domain.models.DemoObject

interface DemoObjectDBMapper : BaseMapper<DemoObject, DemoObjectWithOwner>