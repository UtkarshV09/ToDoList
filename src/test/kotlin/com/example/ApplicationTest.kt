package com.example

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.example.plugins.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Hello Todos!", bodyAsText())
        }
    }
    @Test
    fun testTodos() = testApplication {
        application {
            configureRouting()
        }
        client.get("/todos").apply {
            assertEquals(HttpStatusCode.OK, status)
            // You will need to assert with the actual response body here.
            // Assuming repository.getAllToDos() returns an empty list.
            assertEquals("[]", bodyAsText())
        }
    }

    @Test
    fun testTodoById() = testApplication {
        application {
            configureRouting()
        }
        client.get("/todos/1").apply {
            // Assuming there is no ToDo with ID 1 in the repository.
            assertEquals(HttpStatusCode.NotFound, status)
            assertEquals("Found no todo for the provided ID 1", bodyAsText())
        }
    }

    @Test
    fun testTodoByIdBadRequest() = testApplication {
        application {
            configureRouting()
        }
        client.get("/todos/xyz").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals("Id parameter has to be a number", bodyAsText())
        }
    }
}
