package com.example.testapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.activity.AddTodoActivity
import com.example.testapp.todoData.TodoAdapter
import com.example.testapp.todoData.TodoListDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment(){

    private lateinit var recycleView: RecyclerView
    val adapter = TodoAdapter()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_this_home, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addButtonList = view.findViewById<FloatingActionButton>(R.id.floatingAdd)

        recycleView = view.findViewById(R.id.recycleView)
        recycleView.adapter=adapter

        addButtonList.setOnClickListener {

            startActivity(
                Intent(

                    requireContext(), AddTodoActivity::class.java

                )
            )
        }

    }


    override fun onResume() {
        super.onResume()
        loadTodoData()

    }

   private fun loadTodoData() {
        val listTodo = TodoListDatabase.getInstance(requireContext()).getTodoDao().getTodoList()
        adapter.setTodoList(listTodo)
    }



}
