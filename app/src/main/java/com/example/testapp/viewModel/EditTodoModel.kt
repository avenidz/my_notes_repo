package com.example.testapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditTodoModel : ViewModel() {

    private val _editTodo = MutableStateFlow<EditTodo>(EditTodo.Empty)
    val editTodoState : StateFlow<EditTodo> = _editTodo
    fun editTodoNotes(taskName: String, taskDetails: String, previousName: String?, previousDetails: String?) = viewModelScope.launch {
        _editTodo.value = EditTodo.Loading
        delay(1000)
        if(taskName == ""){
            _editTodo.value = EditTodo.Error("Task name required.")
        }else if(taskDetails == ""){
            _editTodo.value = EditTodo.Error("Task details required.")
        }else if(taskName == previousName && taskDetails == previousDetails){
            _editTodo.value = EditTodo.Error(" No changes made.")
        }else{
            _editTodo.value = EditTodo.Success
        }
    }

    sealed class EditTodo{
        object Success: EditTodo()
        data class Error(val message: String): EditTodo()
        object Loading: EditTodo()
        object Empty: EditTodo()
    }
}