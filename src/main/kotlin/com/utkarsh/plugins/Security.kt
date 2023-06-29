package com.utkarsh.plugins

import io.ktor.server.auth.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.sessions.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSecurity() {

    install(Sessions) {
        cookie<UserSession>("user_session")
    }

    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://127.0.0.1:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("988893156477-50crum21k9dftog0j2f27rmpqrflupm6.apps.googleusercontent.com"),
                    clientSecret = System.getenv("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"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(Apache)
        }
    }

    routing {
        authenticate("auth-oauth-google") {
            get("/login") { // Here's where you start the authentication
                call.respondRedirect("/callback")
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                if (principal != null) {
                    call.sessions.set(UserSession(principal?.accessToken.toString()))
                    call.respondRedirect("/todos")
                }else{
                    call.respond(HttpStatusCode.Unauthorized)
                }
            }
        }
    }
}

class UserSession(accessToken: String)
