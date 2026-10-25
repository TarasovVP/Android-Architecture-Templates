package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.data.database.DemoObjectDao
import com.vnteam.architecturetemplates.data.local.Preferences
import com.vnteam.architecturetemplates.data.mapperimpls.DemoObjectDBMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.DemoObjectResponseMapperImpl
import com.vnteam.architecturetemplates.data.mapperimpls.OwnerResponseMapperImpl
import com.vnteam.architecturetemplates.data.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.data.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.data.network.ApiService
import com.vnteam.architecturetemplates.data.network.ApiServiceImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.ApiRepositoryImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.DBRepositoryImpl
import com.vnteam.architecturetemplates.data.repositoryimpl.PreferencesRepositoryImpl
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectDBMapper
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectUIMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerUIMapper
import com.vnteam.architecturetemplates.domain.repositories.ApiRepository
import com.vnteam.architecturetemplates.domain.repositories.DBRepository
import com.vnteam.architecturetemplates.domain.repositories.PreferencesRepository
import com.vnteam.architecturetemplates.domain.usecase.ClearDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.CreateDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.DeleteDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromApiUseCase
import com.vnteam.architecturetemplates.domain.usecase.GetDemoObjectsFromDBUseCase
import com.vnteam.architecturetemplates.domain.usecase.InsertDemoObjectsUseCase
import com.vnteam.architecturetemplates.domain.usecase.IsDarkThemeUseCase
import com.vnteam.architecturetemplates.domain.usecase.LanguageUseCase
import com.vnteam.architecturetemplates.fake.data.database.FakeDemoObjectDao
import com.vnteam.architecturetemplates.fake.data.local.FakePreferencesFactory
import com.vnteam.architecturetemplates.presentation.mapperimpls.DemoObjectUIMapperImpl
import com.vnteam.architecturetemplates.presentation.mapperimpls.OwnerUIMapperImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.ClearDemoObjectsUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.CreateDemoObjectUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.DeleteDemoObjectUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.GetDemoObjectUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.GetDemoObjectsFromApiUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.GetDemoObjectsFromDBUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.InsertDemoObjectsUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.IsDarkThemeUseCaseImpl
import com.vnteam.architecturetemplates.presentation.usecaseimpl.LanguageUseCaseImpl
import com.vnteam.architecturetemplates.presentation.viewmodels.AppViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.CreateViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.DetailsViewModel
import com.vnteam.architecturetemplates.presentation.viewmodels.ListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val testModule =
    module(true) {
        // Fakes
        single<Preferences> { FakePreferencesFactory() }

        single<DemoObjectDao> { FakeDemoObjectDao() }

        // Api
        single<ApiService> { ApiServiceImpl(get()) }

        single {
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        }
        single {
            HttpClient {
                install(Logging) {
                    level = LogLevel.ALL
                    logger = Logger.DEFAULT
                }
                install(ContentNegotiation) {
                    json(get())
                }
            }
        }

        // Mappers
        single<OwnerResponseMapper> { OwnerResponseMapperImpl() }

        single<DemoObjectResponseMapper> { DemoObjectResponseMapperImpl(get()) }

        single<DemoObjectDBMapper> { DemoObjectDBMapperImpl() }

        single<OwnerUIMapper> { OwnerUIMapperImpl() }

        single<DemoObjectUIMapper> { DemoObjectUIMapperImpl(get()) }

        // Repositories
        single<ApiRepository> { ApiRepositoryImpl(get(), get()) }

        single<DBRepository> { DBRepositoryImpl(get(), get()) }

        single<PreferencesRepository> { PreferencesRepositoryImpl(get()) }

        // UseCases
        single<ClearDemoObjectUseCase> { ClearDemoObjectsUseCaseImpl(get()) }

        single<CreateDemoObjectUseCase> { CreateDemoObjectUseCaseImpl(get()) }

        single<DeleteDemoObjectUseCase> { DeleteDemoObjectUseCaseImpl(get()) }

        single<GetDemoObjectsFromApiUseCase> { GetDemoObjectsFromApiUseCaseImpl(get()) }

        single<GetDemoObjectsFromDBUseCase> { GetDemoObjectsFromDBUseCaseImpl(get()) }

        single<GetDemoObjectUseCase> { GetDemoObjectUseCaseImpl(get(), get()) }

        single<InsertDemoObjectsUseCase> { InsertDemoObjectsUseCaseImpl(get()) }

        single<IsDarkThemeUseCase> { IsDarkThemeUseCaseImpl(get()) }

        single<LanguageUseCase> { LanguageUseCaseImpl(get()) }

        // ViewModels
        viewModel { AppViewModel(get(), get()) }

        viewModel { ListViewModel(get(), get(), get(), get(), get(), get()) }

        viewModel { CreateViewModel(get(), get(), get(), get()) }

        viewModel { DetailsViewModel(get(), get()) }
    }
