package com.vnteam.architecturetemplates.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.vnteam.architecturetemplates.resources.default_text_size

val Typography = Typography(
    bodySmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = default_text_size.textSize
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = default_text_size.textSize
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = default_text_size.textSize
    )
)