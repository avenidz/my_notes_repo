package com.example.testapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.todoData.TodoListDatabase
import com.example.testapp.todoData.UnCheckTodoAdapter


class UnCheckFragment : Fragment() {

    private lateinit var unCheckRecycleView : RecyclerView
    val adapter = UnCheckTodoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_this_uncheck, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        unCheckRecycleView = view.findViewById(R.id.unCheckedRecyclerView)
        unCheckRecycleView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        loadUncheckList()
    }

    private fun loadUncheckList() {
        val todoList = TodoListDatabase.getInstance(requireContext()).getTodoDao().getUncheckTodoList()
        adapter.showListUnCheck(todoList)
    }

}