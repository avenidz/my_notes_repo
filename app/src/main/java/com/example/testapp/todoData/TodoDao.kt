package com.example.testapp.todoData

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT*FROM todo ORDER BY tId ASC")
    fun getTodoList(): List<Todo>

    @Query("SELECT * FROM todo WHERE todoCheck = :todoCheck ORDER BY tId ASC")
    fun getCheckTodoList(todoCheck: Boolean = true): List<Todo>

    @Query("SELECT * FROM todo WHERE todoCheck = :todoCheck ORDER BY tId ASC")
    fun getUncheckTodoList(todoCheck: Boolean = false): List<Todo>

    @Query("SELECT * FROM todo WHERE tId = :todoId")
    fun getTodoById(todoId: String): List<Todo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveTodo(todo: Todo)

    @Update
    fun updateTodo(todo: Todo)

    @Delete
    fun removeTodo(todo: Todo)

}
