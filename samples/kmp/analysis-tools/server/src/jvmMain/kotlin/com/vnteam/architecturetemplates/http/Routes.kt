package com.vnteam.architecturetemplates.http

import com.vnteam.architecturetemplates.DEMO_OBJECTS_ID
import com.vnteam.architecturetemplates.DEMO_OBJECTS_ID_ROUTE
import com.vnteam.architecturetemplates.DEMO_OBJECTS_ROUTE
import com.vnteam.architecturetemplates.demoobjectservice.DemoObjectService
import com.vnteam.architecturetemplates.domain.mappers.DemoObjectResponseMapper
import com.vnteam.architecturetemplates.domain.models.BaseException
import com.vnteam.architecturetemplates.domain.responses.DemoObjectResponse
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

fun Application.apiRoutes(
    demoObjectService: DemoObjectService,
    demoObjectResponseMapper: DemoObjectResponseMapper,
) {
    routing {
        insertDemoObjects(demoObjectService, demoObjectResponseMapper)
        getDemoObjects(demoObjectService, demoObjectResponseMapper)
        getDemoObjectById(demoObjectService, demoObjectResponseMapper)
        deleteDemoObjectById(demoObjectService)
    }
}

fun Routing.insertDemoObjects(
    demoObjectService: DemoObjectService,
    demoObjectResponseMapper: DemoObjectResponseMapper,
) = post(DEMO_OBJECTS_ROUTE) {
    try {
        val demoObjects = demoObjectResponseMapper.mapFromImplModelList(call.receive<List<DemoObjectResponse>>())
        demoObjectService.insertDemoObjects(demoObjects)
        call.respond(HttpStatusCode.Created)
    } catch (e: BaseException) {
        e.printStackTrace()
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.getDemoObjects(
    demoObjectService: DemoObjectService,
    demoObjectResponseMapper: DemoObjectResponseMapper,
) = get(DEMO_OBJECTS_ROUTE) {
    try {
        val demoObjectsList =
            demoObjectResponseMapper
                .mapToImplModelList(demoObjectService.getDemoObjects().orEmpty().toList())
        call.respond(demoObjectsList)
    } catch (e: BaseException) {
        e.printStackTrace()
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.getDemoObjectById(
    demoObjectService: DemoObjectService,
    demoObjectResponseMapper: DemoObjectResponseMapper,
) = get(DEMO_OBJECTS_ID_ROUTE) {
    try {
        val demoObjectId = call.parameters[DEMO_OBJECTS_ID]
        if (demoObjectId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }
        val demoObject =
            demoObjectService
                .getDemoObjectById(demoObjectId)?.let { it1 -> demoObjectResponseMapper.mapToImplModel(it1) }
        if (demoObject != null) {
            call.respond(demoObject)
        } else {
            call.respond(HttpStatusCode.NotFound)
        }
    } catch (e: BaseException) {
        e.printStackTrace()
        call.respond(HttpStatusCode.BadRequest)
    }
}

fun Routing.deleteDemoObjectById(demoObjectService: DemoObjectService) =
    delete(DEMO_OBJECTS_ID_ROUTE) {
        try {
            val demoObjectId = call.parameters[DEMO_OBJECTS_ID]
            if (demoObjectId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            try {
                demoObjectService.deleteDemoObjectById(demoObjectId)
                call.respond(HttpStatusCode.OK)
            } catch (e: BaseException) {
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError)
            }
        } catch (e: BaseException) {
            e.printStackTrace()
            call.respond(HttpStatusCode.BadRequest)
        }
    }
