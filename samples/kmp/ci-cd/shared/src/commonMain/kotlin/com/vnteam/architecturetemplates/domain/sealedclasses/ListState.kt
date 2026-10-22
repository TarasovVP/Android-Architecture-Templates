package com.vnteam.architecturetemplates.domain.sealedclasses

sealed class ListState {
    data object Refresh : ListState()

    data object Details : ListState()

    data object ConfirmDelete : ListState()

    data object Delete : ListState()
}
