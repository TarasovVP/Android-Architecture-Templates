package com.vnteam.architecturetemplates.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.vnteam.architecturetemplates.presentation.components.painterRes
import com.vnteam.architecturetemplates.resources.DrawableResources
import com.vnteam.architecturetemplates.resources.LocalLargePadding
import com.vnteam.architecturetemplates.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.resources.getStringResources
import org.koin.compose.koinInject

@Composable
fun DetailsScreen(demoObjectId: Long?, onClick: () -> Unit) {
    val viewModel = koinInject<DetailsViewModel>()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(demoObjectId) {
        viewModel.processIntent(DetailsIntent.LoadDemoObject(demoObjectId ?: 0))
    }

    DetailsContent(viewState.value, onClick)
}

@Composable
fun DetailsContent(viewState: DetailsViewState, onClick: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalLargePadding.current.margin),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = viewState.demoObject?.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            )
            Text(
                text = viewState.demoObject?.owner?.login.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            )
            OwnerCard(
                avatarUrl = viewState.demoObject?.owner?.avatarUrl.orEmpty(),
                description = viewState.demoObject?.description.orEmpty()
            )
            Text(
                text = "Description:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            )
            Text(
                text = viewState.demoObject?.description.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            )
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            ) {
                Text(text = getStringResources().BACK)
            }
        }
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun OwnerCard(avatarUrl: String, description: String) {
    Card {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
            LocalMediumPadding.current.margin)) {
            SubcomposeAsyncImage(
                model = avatarUrl,
                contentDescription = getStringResources().OWNER_AVATAR,
                modifier = Modifier
                    .wrapContentSize()
                    .width(50.dp)
                    .height(50.dp),
                contentScale = ContentScale.Crop
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    is AsyncImagePainter.State.Error -> Image(painter = painterRes(DrawableResources.IC_PERSON), contentDescription = null)
                    else -> SubcomposeAsyncImageContent()
                }
            }
            Text(
                text = description,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LocalLargePadding.current.margin)
            )
        }
    }
}
