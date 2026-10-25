package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.data.database.DemoObjectDaoImpl
import com.vnteam.architecturetemplates.data.database.SharedDatabase
import com.vnteam.architecturetemplates.data.mapperimpls.DemoObjectDBMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.DemoObjectResponseMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.OwnerResponseMapperImpl
import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.BASE_URL
import com.vnteam.architecturetemplates.data.repositoryimpl.ApiRepositoryImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.DBRepositoryImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.PreferencesRepositoryImpl
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.AppUseCase
import com.vnteam.architecturetemplates.domain.usecase.DetailsUseCase
import com.vnteam.architecturetemplates.domain.usecase.ListUseCase
import com.vnteam.architecturetemplates.presentation.viewmodels.DetailsViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import com.vnteam.architecturetemplates.presentation.mapperimpls.DemoObjectUIMapperImpl
import com.vnteam.architecturetemplates.presentation.mapperimpls.OwnerUIMapperImpl
import com.vnteam.architecturetemplates.presentation.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.presentation.usecaseimpl.ListUseCaseImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import com.vnteam.architecturetemplates.presentation.mappers.OwnerUIMapper
import com.vnteam.architecturetemplates.presentation.usecaseimpl.AppUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.DetailsUseCaseImpl
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

val appModule = module {

    single { BASE_URL }
    single { ApiService(get<String>(), get()) }
    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
            }
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }
    }

    single {
        SharedDatabase(get())
    }

    single<DemoObjectDao> {
        DemoObjectDaoImpl(get())
    }

    single<OwnerResponseMapper> { OwnerResponseMapperImpl() }

    single<DemoObjectResponseMapper> { DemoObjectResponseMapperImpl(get()) }

    single<DemoObjectDBMapper> { DemoObjectDBMapperImpl() }

    single<ApiRepository> { ApiRepositoryImpl(get(), get()) }

    single<DBRepository> { DBRepositoryImpl(get(), get()) }

    single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

    single<OwnerUIMapper> { OwnerUIMapperImpl() }

    single<DemoObjectUIMapper> { DemoObjectUIMapperImpl(get()) }

    single<AppUseCase> { AppUseCaseImpl(get()) }

    single<ListUseCase> { ListUseCaseImpl(get(), get()) }

    single<DetailsUseCase> { DetailsUseCaseImpl(get()) }

    factory {
        ListViewModel(get(), get())
    }
    factory {
        DetailsViewModel(get(), get())
    }
}