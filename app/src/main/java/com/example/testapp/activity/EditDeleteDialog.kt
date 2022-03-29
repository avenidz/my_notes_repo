package com.example.testapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.testapp.R
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase

class EditDeleteDialog(todoSelectedData: List<String>): DialogFragment(){

    private lateinit var buttonEdit : Button
    private lateinit var buttonDelete : Button
    val todoSelectedData = todoSelectedData
    private lateinit var todoData: TodoListDatabase



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getDialog()!!.window?.setBackgroundDrawableResource(R.drawable.custom_dialog)
//        return inflater.inflate(R.layout.todo_edit_delete, container, false)
        val view: View = inflater!!.inflate(R.layout.todo_edit_delete, container, false)

        buttonEdit = view.findViewById(R.id.buttonEdit)
        buttonDelete = view.findViewById(R.id.buttonDelete)
        todoData = TodoListDatabase.getInstance(view.context)


        buttonEdit.setOnClickListener{ //pass value to edit
            this.dismiss()

            view.context.startActivity(
                Intent(
                    view.context, EditTodoActivity()::class.java
                )   .putExtra("TaskId", todoSelectedData[0])
                    .putExtra("TaskName", todoSelectedData[1])
                    .putExtra("TaskCheck", todoSelectedData[2])
                    .putExtra("TaskDetails", todoSelectedData[3])
            )

        }
        buttonDelete.setOnClickListener{ //delete this todolist
            val deleteTodo = Todo(todoSelectedData[0].toInt(), todoSelectedData[1], todoSelectedData[2].toBoolean())
            deleteTodo.detail = todoSelectedData[3]
            todoData.getTodoDao().removeTodo(deleteTodo)
            this.dismiss()



        }

        return view

    }




    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
//        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}