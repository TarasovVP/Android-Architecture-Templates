package com.vnteam.architecturetemplates.content.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.vnteam.architecturetemplates.components.AvatarImage
import com.vnteam.architecturetemplates.components.CommonTextField
import com.vnteam.architecturetemplates.components.HeaderText
import com.vnteam.architecturetemplates.components.PrimaryButton
import com.vnteam.architecturetemplates.presentation.states.CreateViewState
import com.vnteam.architecturetemplates.presentation.uimodels.DemoObjectUI
import com.vnteam.architecturetemplates.resources.LocalDefaultPadding
import com.vnteam.architecturetemplates.resources.LocalLargeAvatarSize
import com.vnteam.architecturetemplates.resources.LocalStringResources

@Composable
fun CreateContent(
    viewState: State<CreateViewState>,
    originDemoObject: MutableState<DemoObjectUI?>,
    onClick: () -> Unit,
) {
    if (originDemoObject.value == null) {
        return
    }
    Box {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(LocalDefaultPadding.current.size),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HeaderText(LocalStringResources.current.demoObject)
            CommonTextField(
                remember { mutableStateOf(TextFieldValue(viewState.value.demoObject.value?.name.orEmpty())) },
                "${LocalStringResources.current.name}*",
            ) { text ->
                viewState.value.demoObject.value =
                    viewState.value.demoObject.value?.copy(name = text)
            }
            CommonTextField(
                remember { mutableStateOf(TextFieldValue(viewState.value.demoObject.value?.description.orEmpty())) },
                LocalStringResources.current.description,
            ) { text ->
                viewState.value.demoObject.value =
                    viewState.value.demoObject.value?.copy(description = text)
            }
            CommonTextField(
                remember { mutableStateOf(TextFieldValue(viewState.value.demoObject.value?.htmlUrl.orEmpty())) },
                LocalStringResources.current.url,
            ) { text ->
                viewState.value.demoObject.value =
                    viewState.value.demoObject.value?.copy(htmlUrl = text)
            }
            HeaderText(LocalStringResources.current.owner)
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier.wrapContentSize().clickable {
                        viewState.value.isChangeAvatarDialogVisible.value = true
                    },
            ) {
                AvatarImage(
                    resId = viewState.value.demoObject.value?.owner?.avatarUrl.orEmpty(),
                    avatarSize = LocalLargeAvatarSize.current.size,
                )
            }
            CommonTextField(
                remember { mutableStateOf(TextFieldValue(viewState.value.demoObject.value?.owner?.login.orEmpty())) },
                "${LocalStringResources.current.name}*",
            ) { text ->
                viewState.value.demoObject.value =
                    viewState.value.demoObject.value?.copy(
                        owner = viewState.value.demoObject.value?.owner?.copy(login = text),
                    )
            }
            CommonTextField(
                remember { mutableStateOf(TextFieldValue(viewState.value.demoObject.value?.owner?.url.orEmpty())) },
                LocalStringResources.current.url,
            ) { text ->
                viewState.value.demoObject.value =
                    viewState.value.demoObject.value?.copy(
                        owner = viewState.value.demoObject.value?.owner?.copy(url = text),
                    )
            }
            PrimaryButton(
                LocalStringResources.current.submit,
                originDemoObject.value !=
                    viewState.value.demoObject.value &&
                    viewState.value.demoObject.value?.isDemoObjectValid() == true,
                Modifier,
                onClick = onClick,
            )
        }
    }
}
