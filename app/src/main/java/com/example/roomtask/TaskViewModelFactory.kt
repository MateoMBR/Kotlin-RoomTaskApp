package com.example.roomtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskViewModelFactory(private val taskDao: TaskDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(TaskViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                TaskViewModel(taskDao) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
