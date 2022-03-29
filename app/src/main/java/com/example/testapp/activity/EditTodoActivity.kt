package com.example.testapp.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testapp.R
import com.example.testapp.UpdateTaskName
import com.example.testapp.todoData.TodoListDatabase

class EditTodoActivity : AppCompatActivity(), UpdateTaskName{

    private lateinit var getName : TextView
    private lateinit var getDetail : TextView
    private lateinit var updateButton : Button
    private lateinit var todoDatabase : TodoListDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getName = findViewById(R.id.todoName)
        getDetail = findViewById(R.id.todoDetails)
        updateButton  = findViewById(R.id.saveButton)

        val taskId = intent.getStringExtra("todoId")
        val taskName = intent.getStringExtra("todoName")
        val taskCheck = intent.getStringExtra("todoCheck")
        val taskDetails = intent.getStringExtra("todoDetails")
        todoDatabase = TodoListDatabase.getInstance(this)

        title = "Edit Task"

        //display task name and details to edit
        getName.text = taskName
        getDetail.text = taskDetails

        //update value
        updateButton.setOnClickListener{
            val taskNameUpdate = getName.text
            val taskDetailUpdate = getDetail.text
            if(taskNameUpdate == null || taskNameUpdate.toString() == ""){
                Toast.makeText(this, "Task name required.",Toast.LENGTH_SHORT).show()
            }else if(taskDetailUpdate == null || taskDetailUpdate.toString() == ""){
                Toast.makeText(this, "Task details required", Toast.LENGTH_SHORT).show()
            }else if(taskNameUpdate.toString() == taskName && taskDetailUpdate.toString() == taskDetails.toString()){
                Toast.makeText(this, "No changes made.", Toast.LENGTH_SHORT).show()
//            }else if(taskDetailUpdate.toString() == taskDetails.toString()){
//                Toast.makeText(this, "No changes made for details.", Toast.LENGTH_SHORT).show()
            }else{

                val taskToUpdate = listOf(taskId.toString(), taskNameUpdate.toString(),taskCheck.toString(), taskDetailUpdate.toString())
                onUpdateTask(taskToUpdate,todoDatabase)


                getName.text = ""
                getDetail.text = ""


                onBackPressed()
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
