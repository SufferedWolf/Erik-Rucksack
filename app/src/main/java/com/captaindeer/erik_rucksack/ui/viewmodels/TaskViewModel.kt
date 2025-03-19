package com.captaindeer.erik_rucksack.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.captaindeer.erik_rucksack.data.LocalDatabaseApp.Task
import com.captaindeer.erik_rucksack.data.ErikRucksackDatabase
import com.captaindeer.erik_rucksack.data.LocalDatabaseApp.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by suffered on 19/03/25
 */

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<Task>>
    private val repository: TaskRepository

    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList.asStateFlow()

    init {
        val taskDao = ErikRucksackDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.readAllData

        viewModelScope.launch {
            readAllData.observeForever{ tasks ->
                _taskList.value = tasks
            }
        }
    }

    fun addTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTask(task)
        }
    }

}