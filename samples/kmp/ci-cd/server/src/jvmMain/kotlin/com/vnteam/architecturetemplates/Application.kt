package com.vnteam.architecturetemplates

import com.vnteam.architecturetemplates.di.serverModule
import com.vnteam.architecturetemplates.http.apiRoutes
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import kotlinx.serialization.json.Json
import org.koin.ktor.ext.get
import org.koin.ktor.plugin.Koin

fun main() {
    embeddedServer(
        Netty,
        host = HTTP_HOST,
        port = System.getenv(PORT)?.toInt() ?: HTTP_PORT,
        module = Application::appModule,
    ).start(wait = true)
}

fun Application.appModule() {
    install(Koin) {
        modules(serverModule)
    }
    val jsonInstance = get<Json>()
    install(ContentNegotiation) {
        json(jsonInstance)
    }
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowMethod(HttpMethod.Delete)
    }
    apiRoutes(get(), get())
}
