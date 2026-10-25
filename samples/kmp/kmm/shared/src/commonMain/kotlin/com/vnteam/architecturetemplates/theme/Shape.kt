package com.vnteam.architecturetemplates.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import com.vnteam.architecturetemplates.resources.large_padding
import com.vnteam.architecturetemplates.resources.medium_padding
import com.vnteam.architecturetemplates.resources.small_padding

val Shapes = Shapes(
    small = RoundedCornerShape(small_padding.margin.value),
    medium = RoundedCornerShape(medium_padding.margin.value),
    large = RoundedCornerShape(large_padding.margin.value)
)