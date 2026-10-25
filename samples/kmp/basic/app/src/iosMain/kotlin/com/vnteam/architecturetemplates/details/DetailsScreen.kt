package com.vnteam.architecturetemplates.details

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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import com.vnteam.architecturetemplates.PlatformMessageDisplayer
import org.koin.compose.koinInject

@Composable
fun DetailsScreen(forkId: Long?) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: DetailsViewModel = koinInject()
    val platformMessageDisplayer: PlatformMessageDisplayer = koinInject()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(forkId) {
        viewModel.processIntent(DetailsIntent.LoadFork(forkId ?: 0))
    }

    LaunchedEffect(forkId) {
        viewModel.getForkById(forkId)
    }
    LaunchedEffect(viewState.value.error) {
        viewState.value.error?.let { message ->
            platformMessageDisplayer.showPopupMessage(message)
        }
    }
    DetailsContent(viewState.value) {
        navigator.pop()
    }
}

@Composable
fun DetailsContent(viewState: DetailsViewState, onClick: () -> Unit) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = viewState.fork?.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = viewState.fork?.owner?.login.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Card {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    SubcomposeAsyncImage(
                        model = viewState.fork?.owner?.avatarUrl.orEmpty(),
                        contentDescription = "Owner avatar",
                        modifier = Modifier
                            .wrapContentSize()
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            //TODO add painterResource
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                    Text(
                        text = viewState.fork?.description.orEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            }
            Text(
                text = "Description:",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = viewState.fork?.description.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Back")
            }
        }
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}