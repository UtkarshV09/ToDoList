package com.example.repository

import com.example.database.DatabaseManager
import com.example.entities.ToDo
import com.example.entities.TodoDraft

class MySQLTodoRepository: ToDoRepository {

    private val database = DatabaseManager()


    override fun getAllToDos(): List<ToDo> {
        return database.getAllTodos().map { ToDo(it.id, it.title, it.done) }
    }

    override fun getToDo(id: Int): ToDo? {

        return database.getTodo(id)
            ?.let { ToDo(it.id, it.title, it.done) }

    }

    override fun addToDo(draft: TodoDraft): ToDo {
        return database.addTodo(draft)

    }

    override fun removeToDo(id: Int): Boolean {
        return database.removeTodo(id)
    }

    override fun updateToDo(id: Int, draft: TodoDraft): Boolean {
        return database.updateTodo(id, draft)
    }

}