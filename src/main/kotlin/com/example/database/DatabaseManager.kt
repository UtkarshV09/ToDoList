package com.example.database

import com.example.database.DBTodoTable.id
import com.example.entities.ToDo
import com.example.entities.TodoDraft
import org.ktorm.database.Database
import org.ktorm.dsl.delete
import org.ktorm.dsl.eq
import org.ktorm.dsl.insertAndGenerateKey
import org.ktorm.dsl.update
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    // config
    private val hostname = "localhost"
    private val databaseName = "ktor_todo"
    private val username = "navicatuser"
    private val password = "Ashu@2909"

    // database
    private val ktoremDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false"
        ktoremDatabase = Database.connect(jdbcUrl)
    }

    fun getAllTodos(): List<DBTodoEntity> {
        return ktoremDatabase.sequenceOf(DBTodoTable).toList()
    }

     fun getTodo(id: Int): DBTodoEntity? {
         return  ktoremDatabase.sequenceOf(DBTodoTable)
             .firstOrNull{it.id eq id}
     }

    fun  addTodo(draft: TodoDraft): ToDo {

        val insertedID = ktoremDatabase.insertAndGenerateKey(DBTodoTable) {
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
        }as Int

        return ToDo(insertedID, draft.title, draft.done)
    }

    fun updateTodo(id: Int, draft: TodoDraft): Boolean {
        val updateRows = ktoremDatabase.update(DBTodoTable){
            set(DBTodoTable.title, draft.title)
            set(DBTodoTable.done, draft.done)
            where {
                it.id eq id
            }
        }

        return updateRows > 0
    }

    fun removeTodo(id: Int): Boolean {
        val deletedRows = ktoremDatabase.delete(DBTodoTable) {
            it.id eq id
        }
        return deletedRows > 0
    }

}