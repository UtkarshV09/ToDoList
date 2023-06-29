package com.utkarsh.repository

import com.utkarsh.entities.ToDo
import com.utkarsh.entities.TodoDraft

interface ToDoRepository {

    fun getAllToDos(): List<ToDo>

    fun getToDo(id: Int): ToDo?

    fun addToDo(draft: TodoDraft): ToDo

    fun removeToDo(id: Int): Boolean

    fun updateToDo(id: Int, draft: TodoDraft): Boolean
}