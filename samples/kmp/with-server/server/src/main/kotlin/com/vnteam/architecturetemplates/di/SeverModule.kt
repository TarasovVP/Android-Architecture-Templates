package com.vnteam.architecturetemplates.di

import com.vnteam.architecturetemplates.ServerDatabase
import com.vnteam.architecturetemplates.ServerDatabase.Companion.Schema
import com.vnteam.architecturetemplates.db.DatabaseDriverFactory
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.mappers.OwnerResponseMapper
import com.vnteam.architecturetemplates.demo_object_service.DemoObjectService
import com.vnteam.architecturetemplates.demo_object_service.DemoObjectServiceImpl
import com.vnteam.architecturetemplates.mapperimpls.DemoObjectResponseMapperImpl
import com.vnteam.architecturetemplates.mapperimpls.OwnerResponseMapperImpl
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val serverModule = module {
    single {
        DatabaseDriverFactory()
    }
    single { get<DatabaseDriverFactory>().createDriver() }
    single {
        ServerDatabase(get()).apply {
            Schema.create(get())
        }
    }
    single {
        get<ServerDatabase>().serverDatabaseQueries
    }
    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
    single<OwnerResponseMapper> { OwnerResponseMapperImpl() }
    single<DemoObjectResponseMapper> { DemoObjectResponseMapperImpl(get()) }
    single<DemoObjectService> {
        DemoObjectServiceImpl(get())
    }
}