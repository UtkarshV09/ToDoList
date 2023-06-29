package com.utkarsh.plugins

import com.utkarsh.entities.TodoDraft
import com.utkarsh.repository.MySQLTodoRepository
import com.utkarsh.repository.ToDoRepository
import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*

fun Application.configureRouting() {

    routing {

        val repository: ToDoRepository = MySQLTodoRepository()

        get("/") {
            call.respondText("Hello Todos!")
        }


        get("/todos") {
            call.respond(repository.getAllToDos())
        }
        get("/todos/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Id parameter has to be a number"
                )
                return@get
            }

            val todo = repository.getToDo(id)
            if (todo == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Found no toto for the provided ID $id"
                )
            } else {
                call.respond(todo)
            }

        }
        post("/todos") {
            val todoDraft = call.receive<TodoDraft>()
            val todo = repository.addToDo(todoDraft)
            call.respond(todo)
        }
        put("todos/{id}") {
            val todoDraft = call.receive<TodoDraft>()
            val todoId = call.parameters["id"]?.toIntOrNull()

            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "id parameter has to be number"
                )
                return@put
            }

            val updated = repository.updateToDo(todoId, todoDraft)
            if (updated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.NotFound,
                    "found no todo with the id $todoId"
                )
            }

        }
        delete("/todos/{id}") {
            val todoId = call.parameters["id"]?.toIntOrNull()

            if (todoId == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "id parameter has to be number"
                )
                return@delete

            }

            val removed = repository.removeToDo(todoId)
            if (removed){
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.NotFound,
                    "found no todo with the id $todoId")
            }
        }
    }
}