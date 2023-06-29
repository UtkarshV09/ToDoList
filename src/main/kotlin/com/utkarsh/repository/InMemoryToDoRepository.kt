package com.utkarsh.repository

import com.utkarsh.entities.ToDo
import com.utkarsh.entities.TodoDraft

class InMemoryToDoRepository : ToDoRepository{

    private val todos = mutableListOf<ToDo>(

    )

    override fun getAllToDos(): List<ToDo> {
        return todos
    }

    override fun getToDo(id: Int): ToDo? {
        return todos.firstOrNull { it.id == id }
    }

    override fun addToDo(draft: TodoDraft): ToDo {

        val todo = ToDo(
            id = todos.size + 1,
            title = draft.title,
            done = draft.done
        )
        todos.add(todo)

        return todo

    }

    override fun removeToDo(id: Int): Boolean {
        return todos.removeIf{ it.id == id}
    }

    override fun updateToDo(id: Int, draft: TodoDraft): Boolean {
        val todo = todos.firstOrNull{ it.id == id}
            ?: return false

        todo.title = draft.title
        todo.done = draft.done
        return  true
    }
}