package com.vnteam.architecturetemplates.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.vnteam.architecturetemplates.UIConstants
import com.vnteam.architecturetemplates.UIConstants.SPLASH_ANIMATION_DURATION
import com.vnteam.architecturetemplates.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.resources.Res
import com.vnteam.architecturetemplates.resources.android_architecture_template
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen() {
    val screenWidth = remember { mutableStateOf(0.dp) }
    val minSize = screenWidth.value * UIConstants.FLOAT_2
    val maxSize = screenWidth.value * UIConstants.FLOAT_7

    val infiniteTransition = rememberInfiniteTransition()
    val size =
        infiniteTransition.animateFloat(
            initialValue = minSize.value,
            targetValue = maxSize.value,
            animationSpec =
                infiniteRepeatable(
                    animation =
                        tween(
                            durationMillis = SPLASH_ANIMATION_DURATION,
                            easing = LinearEasing,
                        ),
                    repeatMode = RepeatMode.Reverse,
                ),
        )

    val localDensity = LocalDensity.current
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .onSizeChanged { newSize ->
                    with(localDensity) {
                        screenWidth.value = newSize.width.toDp()
                    }
                },
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(Res.drawable.android_architecture_template),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .size(size.value.dp)
                    .clip(RoundedCornerShape(LocalLargeAvatarSize.current.size)),
        )
    }
}
