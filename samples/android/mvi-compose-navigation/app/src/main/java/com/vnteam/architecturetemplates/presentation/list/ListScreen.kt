package com.vnteam.architecturetemplates.presentation.list

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vnteam.architecturetemplates.presentation.navigation.NavigationScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ListScreen() {
    val navigator = LocalNavigator.currentOrThrow
    val viewModel: ListViewModel = koinViewModel()
    val viewState = viewModel.state.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(viewState.value.error) {
        viewState.value.error?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
    ListContent(viewState.value, { demoObjectId ->
        navigator.push(NavigationScreen.DetailsContentScreen(demoObjectId))
    }, {
        viewModel.getDemoObjectsFromApi()
    })
}

@Composable
fun ListContent(viewState: ListViewState, onItemClick: (Long) -> Unit, onButtonClick: () -> Unit) {

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Button(
                onClick = onButtonClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Start")
            }
            LazyColumn {
                items(viewState.demoObjects.orEmpty()) { item ->
                    Card(modifier = Modifier.padding(8.dp)) {
                        Text(text = item.name.orEmpty(), modifier = Modifier
                            .padding(8.dp)
                            .clickable { onItemClick(item.id ?: 0) })
                    }
                }
            }
        }
        if (viewState.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}