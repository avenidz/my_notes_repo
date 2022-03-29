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

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(),AdapterFunctionCallback, ShowingDetails {

    val todoList: MutableList<Todo> = mutableListOf()
    private lateinit var todoDatabase: TodoListDatabase
//    private lateinit var todoChecked: CheckBox

    inner class TodoViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(todo: Todo) {

            val todoName = view.findViewById<TextView>(R.id.todoName)
            val todoDetails = view.findViewById<TextView>(R.id.todoDetails)
            val todoChecked = view.findViewById<CheckBox>(R.id.todoCheckBox)
            todoDatabase = TodoListDatabase.getInstance(todoChecked.context)

            todoName.text = todo.title
            todoDetails.text = todo.detail

            when(todo.check){
                true -> todoChecked.isChecked = true
                false -> todoChecked.isChecked = false
            }

            todoChecked.setOnClickListener {
                if(todoChecked.isChecked){
                    onCheckBoxChecked(todo,true)
                }else{
                    onCheckBoxChecked(todo, false)
                }
            }

            view.setOnClickListener{

                onViewItemDetails(view, todo)

            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        return TodoViewHolder(inflater.inflate(R.layout.item_view_todo, parent, false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setTodoList(list: List<Todo>) {
        todoList.clear()
        todoList.addAll(list)
        notifyDataSetChanged()
    }



    override fun onCheckBoxChecked(todo: Todo, thisBol: Boolean) {
        val todos = Todo(todo.tId,todo.title,thisBol)
        todos.detail = todo.detail
        todoDatabase.getTodoDao().updateTodo(todos)

        var loadAfterUpdate = todoDatabase.getTodoDao().getTodoList()
        setTodoList(loadAfterUpdate)
    }



//      private fun showEditDeleteDialog(todo: Todo, view: View){
//          val todoSelectedData = listOf(todo.tId.toString(), todo.title, todo.check.toString(), todo.detail)
//          val fragmentManager = (view.context as AppCompatActivity).supportFragmentManager.beginTransaction()
//          EditDeleteDialog(todoSelectedData).show(fragmentManager,"Dialog")
//      }


}