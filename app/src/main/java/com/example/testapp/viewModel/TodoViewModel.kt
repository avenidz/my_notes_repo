package com.example.testapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testapp.todoData.Todo

class TodoViewModel : ViewModel() {
    var thisData = 0
    val viewDetails : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }
    var viewTodo = listOf<Todo>()
    val viewTodoDetails : MutableLiveData<List<Todo>> by lazy {
        MutableLiveData<List<Todo>>()
    }

}