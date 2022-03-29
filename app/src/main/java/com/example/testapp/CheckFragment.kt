package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.todoData.CheckTodoAdapter
import com.example.testapp.todoData.Todo
import com.example.testapp.todoData.TodoListDatabase

class CheckFragment : Fragment() {

    private lateinit var checkRecyclerView: RecyclerView
    val adapter = CheckTodoAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_this_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkRecyclerView=view.findViewById(R.id.checkRecycleView)
        checkRecyclerView.adapter = adapter
//        val ifEmptyCheckTodo = listOf<Todo>(
//            Todo(1,"Empty list",true)
//        )
//        adapter.showCheckTodoList()


    }

    override fun onResume() {
        super.onResume()
        loadCheckList()
    }

    private fun loadCheckList() {
        val listCheckTodo = TodoListDatabase.getInstance(requireContext()).getTodoDao().getCheckTodoList()
        adapter.showCheckTodoList(listCheckTodo)    }
}