package com.pratthamarora.routes

import com.pratthamarora.auth.JwtService
import com.pratthamarora.auth.MySession
import com.pratthamarora.repository.Repository
import io.ktor.application.application
import io.ktor.application.call
import io.ktor.application.log
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.post
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Route
import io.ktor.sessions.sessions
import io.ktor.sessions.set

@KtorExperimentalLocationsAPI
fun Route.users(
    db: Repository,
    jwtService: JwtService,
    hashFunction: (String) -> String
) {
    post<UserCreateRoute> {
        val signupParameters = call.receive<Parameters>()
        val password = signupParameters["password"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields"
            )
        val displayName = signupParameters["displayName"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields"
            )
        val email = signupParameters["email"]
            ?: return@post call.respond(
                HttpStatusCode.Unauthorized, "Missing Fields"
            )
        val hash = hashFunction(password)
        try {
            val newUser = db.addUser(email, displayName, hash)
            newUser?.userId?.let {
                call.sessions.set(MySession(it))
                call.respondText(
                    jwtService.generateToken(newUser),
                    status = HttpStatusCode.Created
                )
            }
        } catch (e: Throwable) {
            application.log.error("Failed to register user", e)
            call.respond(HttpStatusCode.BadRequest, "Problems creating User")
        }
    }
}