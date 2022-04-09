package com.example.testapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.testapp.UpdateTaskName
import com.example.testapp.databinding.ActivityEditTodoBinding
import com.example.testapp.todoData.TodoListDatabase
import com.example.testapp.viewModel.EditTodoModel
import kotlinx.coroutines.flow.collect

class EditTodoActivity : AppCompatActivity(), UpdateTaskName{

    private lateinit var todoDatabase : TodoListDatabase

    private lateinit var binding: ActivityEditTodoBinding
    private val viewModel: EditTodoModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val taskId = intent.getStringExtra("todoId")
        val taskName = intent.getStringExtra("todoName")
        val taskCheck = intent.getStringExtra("todoCheck")
        val taskDetails = intent.getStringExtra("todoDetails")
        todoDatabase = TodoListDatabase.getInstance(this)

        title = "Edit Task"

        //display task name and details to edit
        binding.todoName.setText(taskName)
        binding.todoDetails.setText(taskDetails)

        binding.saveButton.setOnClickListener{
            viewModel.editTodoNotes(
                binding.todoName.text.toString(),
                binding.todoDetails.text.toString(),
                taskName, taskDetails
            )
        }
        lifecycleScope.launchWhenStarted {
            viewModel.editTodoState.collect{
                when(it){
                    is EditTodoModel.EditTodo.Success ->{
                        //update value
                        val taskToUpdate = listOf(taskId.toString(), binding.todoName.text.toString(),taskCheck.toString(), binding.todoDetails.text.toString())
                        onUpdateTask(taskToUpdate,todoDatabase)

                        binding.todoName.text.clear()
                        binding.todoDetails.text.clear()

                        onBackPressed()
                    }
                    is EditTodoModel.EditTodo.Error ->{
                        Toast.makeText(binding.saveButton.context, it.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
