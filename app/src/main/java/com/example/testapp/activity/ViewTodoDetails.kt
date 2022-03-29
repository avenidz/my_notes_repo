package com.example.testapp.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase
import com.example.testapp.todoData.ViewTodoAdapter
import com.example.testapp.viewModel.TodoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ViewTodoDetails : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    val adapter = ViewTodoAdapter()
    private lateinit var todoData : TodoListDatabase

    lateinit var viewModel: TodoViewModel
    lateinit var getTodoId: String
    lateinit var itTodoData: List<Todo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        title = "Details"

        getTodoId = intent.getStringExtra("TodoId").toString()

        recyclerView = findViewById(R.id.viewRecyclerView)
        recyclerView.adapter = adapter
        todoData = TodoListDatabase.getInstance(this)

        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)
        showText()
        viewModel.viewTodoDetails.observe(this, Observer {
            itTodoData = it
            if(itTodoData == null){
                onBackPressed()
            }else{
                adapter.setViewTodo(itTodoData)
            }
        })

    }
    private fun showText(){
//        viewModel.viewDetails.value = ++viewModel.thisData;
        todoData = TodoListDatabase.getInstance(this)
        val getDataPushToModel = todoData.getTodoDao().getTodoById(getTodoId)
        viewModel.viewTodo = getDataPushToModel
        viewModel.viewTodoDetails.value = viewModel.viewTodo
    }

    override fun onResume() {
        super.onResume()
        showText()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.toolbarDelete -> { //delete todo
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.apply {
                    setTitle("Alert")
                    setMessage("Continue delete?")
                    setPositiveButton("Delete") { _, _ ->
                        val deleteTodo = Todo(itTodoData[0].tId, itTodoData[0].title, itTodoData[0].check)
                        deleteTodo.detail = itTodoData[0].detail
                        todoData.getTodoDao().removeTodo(deleteTodo)
                        onBackPressed()
                    }
                }.create().show()

                return true
            }
            R.id.toolbarEdit -> { //edit todo
                startActivity(
                    Intent(
                        this, EditTodoActivity()::class.java
                    ).putExtra("todoId", itTodoData[0].tId.toString())
                        .putExtra("todoName", itTodoData[0].title)
                        .putExtra("todoCheck", itTodoData[0].check.toString())
                        .putExtra("todoDetails", itTodoData[0].detail)
                )
                return true
            }
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
//

        return super.onOptionsItemSelected(item)
    }
//
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        return true
    }



}