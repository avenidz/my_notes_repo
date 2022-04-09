package com.example.testapp.activity


import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testapp.databinding.ActivityAddTodoBinding
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase
import com.example.testapp.viewModel.AddTodoModel
import kotlinx.coroutines.flow.collect

class AddTodoActivity : AppCompatActivity() {

    private lateinit var todoDatabase: TodoListDatabase


    private lateinit var binding: ActivityAddTodoBinding
    private val viewModel: AddTodoModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoDatabase = TodoListDatabase.getInstance(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        title = "Add new list"

        binding.saveButton.setOnClickListener{
            viewModel.addTodoNotes(
                binding.todoName.text.toString(),
                binding.todoDetails.text.toString()
            )
        }
        lifecycleScope.launchWhenStarted {
            viewModel.addTodoState.collect {
                when(it){
                    is AddTodoModel.AddTodo.Success -> {
                        val todo = Todo(0, binding.todoName.text.toString(), false)
                        todo.detail = binding.todoDetails.text.toString()
                        todoDatabase.getTodoDao().saveTodo(todo)

                        binding.todoName.text.clear()
                        binding.todoDetails.text.clear()

                        Toast.makeText(binding.saveButton.context, "Success", Toast.LENGTH_SHORT).show()
                    }
                    is AddTodoModel.AddTodo.Error -> {
                        Toast.makeText(binding.saveButton.context, it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
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