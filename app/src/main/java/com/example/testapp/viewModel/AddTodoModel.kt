package com.example.testapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddTodoModel : ViewModel(){

    private val _addTodo = MutableStateFlow<AddTodo>(AddTodo.Empty)
    val addTodoState: StateFlow<AddTodo> = _addTodo

    fun addTodoNotes(taskName: String, taskDetails: String) = viewModelScope.launch {
        _addTodo.value = AddTodo.Loading
        delay(1000)
        if(taskName == ""){
            _addTodo.value = AddTodo.Error("Title required.")
        }else if(taskDetails == ""){
            _addTodo.value = AddTodo.Error("Details required.")
        }else{
            _addTodo.value = AddTodo.Success
        }
    }

    sealed class AddTodo{
        object Success: AddTodo()
        data class Error(val message: String): AddTodo()
        object Loading: AddTodo()
        object Empty: AddTodo()
    }
}