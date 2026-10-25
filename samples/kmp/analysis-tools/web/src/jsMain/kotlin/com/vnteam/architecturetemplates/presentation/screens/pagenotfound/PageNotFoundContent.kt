package com.vnteam.architecturetemplates.presentation.screens.pagenotfound

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import com.vnteam.architecturetemplates.components.FLOAT_3
import com.vnteam.architecturetemplates.components.HeaderText
import com.vnteam.architecturetemplates.resources.LocalLargePadding
import com.vnteam.architecturetemplates.resources.LocalStringResources
import com.vnteam.architecturetemplates.resources.Res
import com.vnteam.architecturetemplates.resources.ic_page_not_found
import org.jetbrains.compose.resources.painterResource

@Composable
fun PageNotFound() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(FLOAT_3))
        Image(
            painter = painterResource(Res.drawable.ic_page_not_found),
            contentDescription = LocalStringResources.current.pageNotFound,
            modifier = Modifier.padding(bottom = LocalLargePadding.current.size),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
        )
        HeaderText(LocalStringResources.current.pageNotFound)
    }
}
