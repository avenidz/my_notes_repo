package com.example.testapp.todoData

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R

class ViewTodoAdapter : RecyclerView.Adapter<ViewTodoAdapter.ViewTodoHolder>() {

    val todoList: MutableList<Todo> = mutableListOf()

    inner class ViewTodoHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(todo: Todo) {
            val thisName = view.findViewById<TextView>(R.id.viewTodoName)
            val thisDetails = view.findViewById<TextView>(R.id.viewTodoDetails)
            thisName.text = todo.title
            thisDetails.text = todo.detail
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTodoHolder {
      var inflater = LayoutInflater.from(parent.context)
        return ViewTodoHolder(inflater.inflate(R.layout.view_todo_details, parent, false))
    }

    override fun onBindViewHolder(holder: ViewTodoHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
      return todoList.size
    }
    fun setViewTodo(list: List<Todo>){
        todoList.clear()
        todoList.addAll(list)
        notifyDataSetChanged()
    }


}

