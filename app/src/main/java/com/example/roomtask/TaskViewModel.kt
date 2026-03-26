package com.example.roomtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskViewModel(private val taskDao: TaskDao) : ViewModel() {
    
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()
    
    private val _taskInput = MutableStateFlow("")
    val taskInput: StateFlow<String> = _taskInput.asStateFlow()
    
    init {
        observeTasks()
    }
    
    private fun observeTasks() {
        viewModelScope.launch {
            taskDao.getAll().collect { taskList ->
                _tasks.value = taskList
            }
        }
    }
    
    fun updateTaskInput(newValue: String) {
        _taskInput.value = newValue
    }
    
    fun addTask() {
        if (_taskInput.value.isNotBlank()) {
            viewModelScope.launch {
                taskDao.insert(Task(title = _taskInput.value))
                _taskInput.value = ""
            }
        }
    }
    
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
        }
    }
}
