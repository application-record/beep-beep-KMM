package org.thechance.serivce_identity.endpoints

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.testRoutes(){
    get("/test"){
        call.respond("welcome to restaurant service")
    }
}
