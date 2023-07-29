package org.thechance.service_chat.endpoints

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.testRoutes(){
    get("/test"){
        call.respond("welcome to chat service")
    }
}
