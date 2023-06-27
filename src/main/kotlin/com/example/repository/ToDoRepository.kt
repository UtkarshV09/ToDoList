package com.example.repository

import com.example.entities.ToDo
import com.example.entities.TodoDraft

interface ToDoRepository {

    fun getAllToDos(): List<ToDo>

    fun getToDo(id: Int): ToDo?

    fun addToDo(draft: TodoDraft): ToDo

    fun removeToDo(id: Int): Boolean

    fun updateToDo(id: Int, draft: TodoDraft): Boolean
}