package com.vnteam.architecturetemplates.presentation.details

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vnteam.architecturetemplates.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(demoObjectId: Long?) {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: DetailsViewModel = koinViewModel()
    val viewState = viewModel.state.collectAsState()

    LaunchedEffect(demoObjectId) {
        viewModel.processIntent(DetailsIntent.LoadDemoObject(demoObjectId ?: 0))
    }

    LaunchedEffect(demoObjectId) {
        viewModel.getDemoObjectById(demoObjectId)
    }
    val context = LocalContext.current
    LaunchedEffect(viewState.value.error) {
        viewState.value.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
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
                text = viewState.demoObject?.name.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = viewState.demoObject?.owner?.login.orEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Card {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(viewState.demoObject?.owner?.avatarUrl.orEmpty())
                            .crossfade(true)
                            .error(R.drawable.ic_person)
                            .placeholder(R.drawable.ic_person)
                            .build(),
                        contentDescription = "Owner avatar",
                        modifier = Modifier
                            .wrapContentSize()
                            .width(50.dp)
                            .height(50.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = viewState.demoObject?.description.orEmpty(),
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
                text = viewState.demoObject?.description.orEmpty(),
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