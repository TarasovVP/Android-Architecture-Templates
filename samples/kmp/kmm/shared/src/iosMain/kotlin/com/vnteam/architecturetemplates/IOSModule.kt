package com.vnteam.architecturetemplates

import com.vnteam.architecturetemplates.data.database.DatabaseDriverFactory
import com.vnteam.architecturetemplates.presentation.details.DetailsViewModel
import com.vnteam.architecturetemplates.presentation.list.ListViewModel
import org.koin.dsl.module

val iosModule = module {
    single {
        DatabaseDriverFactory()
    }
    factory {
        ListViewModel(get(), get())
    }
    factory {
        DetailsViewModel(get(), get())
    }
}