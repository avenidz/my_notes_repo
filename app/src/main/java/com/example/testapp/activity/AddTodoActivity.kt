package com.example.testapp.activity


import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase

class AddTodoActivity : AppCompatActivity() {

    private lateinit var todoDatabase: TodoListDatabase

    private lateinit var todoName: EditText
    private lateinit var todoDetails: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        todoDatabase = TodoListDatabase.getInstance(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todoName = findViewById(R.id.todoName)
        todoDetails = findViewById(R.id.todoDetails)
        saveButton = findViewById(R.id.saveButton)

        title = "Add new list"

        val todoNameValue = todoName.getText()
        val todoDetailsValue = todoDetails.getText()

        saveButton.setOnClickListener {
            if (todoNameValue == null || todoNameValue.toString() == "") {
                Toast.makeText(this, "Task name required.", Toast.LENGTH_SHORT).show()
            } else if (todoDetailsValue == null || todoDetailsValue.toString() == "") {
                Toast.makeText(this, "Details required.", Toast.LENGTH_SHORT).show()
            } else {
                val todo = Todo(0, todoNameValue.toString(), false)
                todo.detail = todoDetailsValue.toString()
                todoDatabase.getTodoDao().saveTodo(todo)
                todoName.text.clear()
                todoDetails.text.clear()
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}