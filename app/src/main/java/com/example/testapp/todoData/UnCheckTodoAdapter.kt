package com.example.testapp.todoData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.AdapterFunctionCallback
import com.example.testapp.R
import com.example.testapp.ShowingDetails

class UnCheckTodoAdapter:RecyclerView.Adapter<UnCheckTodoAdapter.UnCheckTodoHolder>(), AdapterFunctionCallback, ShowingDetails{

    val todoList : MutableList<Todo> = mutableListOf()
    private lateinit var todoDatabase : TodoListDatabase

    inner class UnCheckTodoHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(todo: Todo) {

            val todoName = view.findViewById<TextView>(R.id.todoName)
            val todoDetails = view.findViewById<TextView>(R.id.todoDetails)
            val todoChexkbox = view.findViewById<CheckBox>(R.id.todoCheckBox)
            todoDatabase = TodoListDatabase.getInstance(todoChexkbox.context)

            todoName.text = todo.title
            todoDetails.text = todo.detail
            todoChexkbox.isChecked = false

            todoChexkbox.setOnClickListener {
                if(todoChexkbox.isChecked){
                    onCheckBoxChecked(todo, true)
                }
            }
            view.setOnClickListener{

                onViewItemDetails(view, todo)

            }

        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnCheckTodoHolder {
        val inflater = LayoutInflater.from(parent.context)
        return UnCheckTodoHolder(inflater.inflate(R.layout.item_view_todo,parent,false))
    }

    override fun onBindViewHolder(holder: UnCheckTodoHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
    fun showListUnCheck(list: List<Todo>) {
        todoList.clear()
        todoList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCheckBoxChecked(todo: Todo, thisBol: Boolean) {
        val todos = Todo(todo.tId, todo.title, thisBol)
        todos.detail = todo.detail
        todoDatabase.getTodoDao().updateTodo(todos)

        var loadAfterChanged = todoDatabase.getTodoDao().getUncheckTodoList()
        showListUnCheck(loadAfterChanged)
    }



}
