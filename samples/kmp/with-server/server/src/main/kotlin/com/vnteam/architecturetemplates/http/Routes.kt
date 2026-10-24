package com.vnteam.architecturetemplates.http

import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.responses.DemoObjectResponse
import com.vnteam.architecturetemplates.demo_object_service.DemoObjectService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.apiRoutes(demoObjectService: DemoObjectService, demoObjectResponseMapper: DemoObjectResponseMapper) {
    routing {
        insertDemoObjects(demoObjectService, demoObjectResponseMapper)
        getDemoObjects(demoObjectService, demoObjectResponseMapper)
        getDemoObjectById(demoObjectService, demoObjectResponseMapper)
        deleteDemoObjectById(demoObjectService)
    }
}
fun Routing.insertDemoObjects(demoObjectService: DemoObjectService, demoObjectResponseMapper: DemoObjectResponseMapper) = post("/demoObjects") {
    try {
        val demoObjects = demoObjectResponseMapper.mapFromImplModelList(call.receive<List<DemoObjectResponse>>())
        demoObjectService.insertDemoObjects(demoObjects)
        call.respond(HttpStatusCode.Created)
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.getDemoObjects(demoObjectService: DemoObjectService, demoObjectResponseMapper: DemoObjectResponseMapper) = get("/demoObjects") {
    try {
        val demoObjectsList = demoObjectResponseMapper.mapToImplModelList(demoObjectService.getDemoObjects().orEmpty().toList())
        call.respond(demoObjectsList)
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.getDemoObjectById(demoObjectService: DemoObjectService, demoObjectResponseMapper: DemoObjectResponseMapper) = get("/demoObjects/{id}") {
    try {
        val demoObjectId = call.parameters["id"]
        if (demoObjectId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        val demoObject = demoObjectService.getDemoObjectById(demoObjectId)?.let { it1 -> demoObjectResponseMapper.mapToImplModel(it1) }
        if (demoObject != null) {
            call.respond(demoObject)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.deleteDemoObjectById(demoObjectService: DemoObjectService) = delete("/demoObjects/{id}") {
    try {
        val demoObjectId = call.parameters["id"]
        if (demoObjectId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        try {
            demoObjectService.deleteDemoObjectById(demoObjectId)
            call.respond(HttpStatusCode.OK)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError)
        }
    } catch (e: Exception) {
        call.respond(HttpStatusCode.BadRequest)
    }
}