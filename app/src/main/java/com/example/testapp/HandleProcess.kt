package com.example.testapp


import android.content.Intent
import android.view.View
import android.widget.CheckBox
import com.example.testapp.activity.ViewTodoDetails
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase


interface AdapterFunctionCallback { //callback to edit checked value
    fun onCheckBoxChecked(todo: Todo, thisBol: Boolean)

}
interface ShowingDetails{ //used by 3 adapter (TodoAdapter, CheckTodoAdapter, UnCheckTodoAdapter)
    fun onViewItemDetails(view: View, todo: Todo){
        view.context.startActivity(
            Intent(
                view.context, ViewTodoDetails()::class.java
            )
                .putExtra("TodoId", todo.tId.toString())
                .putExtra("TodoName", todo.title)
                .putExtra("TodoCheck", todo.check.toString())
                .putExtra("TodoDetails", todo.detail)
        )
    }
}
interface UpdateTaskName{ //callback when applying changes to edited task
    fun onUpdateTask(taskToUpdate: List<String>, todoDatabase: TodoListDatabase) {

        val updateTodos = Todo(taskToUpdate[0].toInt(),taskToUpdate[1],taskToUpdate[2].toBoolean())
        updateTodos.detail = taskToUpdate[3]
        todoDatabase.getTodoDao().updateTodo(updateTodos)
    }
}