package presentation.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vnteam.architecturetemplates.Res
import com.vnteam.architecturetemplates.ic_voice
import com.vnteam.architecturetemplates.presentation.TextToSpeechHelper
import com.vnteam.architecturetemplates.presentation.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.presentation.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalMediumPadding
import com.vnteam.architecturetemplates.presentation.resources.LocalStringResources
import com.vnteam.architecturetemplates.presentation.states.DetailsViewState
import com.vnteam.architecturetemplates.presentation.uimodels.OwnerUI
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import presentation.components.AvatarImage
import presentation.components.HeaderText
import presentation.components.PrimaryText
import presentation.components.SecondaryText
import presentation.textWithNoDataHandling

@Composable
fun DetailsContent(viewState: DetailsViewState) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(LocalDefaultPadding.current.size),
            verticalArrangement = Arrangement.Top
        ) {
            HeaderText(LocalStringResources.current.DEMO_OBJECT)
            Row {
                SecondaryText(LocalStringResources.current.NAME)
                PrimaryText(viewState.demoObjectUI?.name.textWithNoDataHandling())
            }
            Row {
                SecondaryText(LocalStringResources.current.DESCRIPTION)
                PrimaryText(viewState.demoObjectUI?.description.textWithNoDataHandling())
            }
            Row(modifier = Modifier.padding(top = LocalMediumPadding.current.size).clickable {
                //shareLink(viewState.demoObject?.htmlUrl.orEmpty())
            }) {
                SecondaryText(LocalStringResources.current.URL)
                PrimaryText(viewState.demoObjectUI?.htmlUrl.textWithNoDataHandling())
            }
            HeaderText(LocalStringResources.current.OWNER)
            OwnerCard(viewState.demoObjectUI?.owner)
        }
    }
}

@Composable
fun OwnerCard(ownerUI: OwnerUI?) {
    //TODO improve text to speech
    val textToSpeechHelper = koinInject<TextToSpeechHelper>()
    Card(modifier = Modifier.padding(top = LocalMediumPadding.current.size).fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(LocalMediumPadding.current.size)
        ) {
            AvatarImage(ownerUI?.avatarUrl.orEmpty(), LocalLargeAvatarSize.current.size)
            Column(modifier = Modifier.padding(start = LocalDefaultPadding.current.size, bottom = LocalMediumPadding.current.size))  {
                PrimaryText(ownerUI?.login.textWithNoDataHandling())
                SecondaryText(ownerUI?.url.textWithNoDataHandling())
            }
            IconButton(onClick = {
                textToSpeechHelper.speak("Owner name: ${ownerUI?.login.orEmpty()} Owner url: ${ownerUI?.url.orEmpty()}")
            }) {
                Icon(painterResource(Res.drawable.ic_voice), contentDescription = null)
            }
        }
    }
}
