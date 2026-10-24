package com.vnteam.architecturetemplates.resources

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val SMALL_SIZE = 4
private const val MEDIUM_SIZE = 8
private const val DEFAULT_SIZE = 16
private const val MEDIUM_LARGE_SIZE = 20
private const val LARGE_SIZE = 32
private const val LARGER_SIZE = 48
private const val MEDIUM_TEXT_SIZE = 16
private const val LARGE_TEXT_SIZE = 24
private const val BIG_SIZE = 75

@Immutable
data class Dimens(val size: Dp = 0.dp, val textSize: TextUnit = 0.sp)

val small_padding = Dimens(size = SMALL_SIZE.dp)
val medium_padding = Dimens(size = MEDIUM_SIZE.dp)
val default_padding = Dimens(size = DEFAULT_SIZE.dp)
val medium_large_padding = Dimens(size = MEDIUM_LARGE_SIZE.dp)
val large_padding = Dimens(size = LARGE_SIZE.dp)
val larger_padding = Dimens(size = LARGER_SIZE.dp)
val default_text_size = Dimens(textSize = MEDIUM_TEXT_SIZE.sp)
val medium_text_size = Dimens(textSize = LARGE_TEXT_SIZE.sp)

private val small_avatar_size = Dimens(size = DEFAULT_SIZE.dp)
private val medium_avatar_size = Dimens(size = LARGE_SIZE.dp)
private val large_avatar_size = Dimens(size = BIG_SIZE.dp)

val LocalSmallAvatarSize = staticCompositionLocalOf { small_avatar_size }
val LocalMediumAvatarSize = staticCompositionLocalOf { medium_avatar_size }
val LocalLargeAvatarSize = staticCompositionLocalOf { large_avatar_size }
val LocalSmallPadding = staticCompositionLocalOf { small_padding }
val LocalMediumPadding = staticCompositionLocalOf { medium_padding }
val LocalDefaultPadding = staticCompositionLocalOf { default_padding }
val LocalMediumLargePadding = staticCompositionLocalOf { medium_large_padding }
val LocalLargePadding = staticCompositionLocalOf { large_padding }
val LocalLargerPadding = staticCompositionLocalOf { larger_padding }
val LocalMediumTextSize = staticCompositionLocalOf { medium_text_size }
