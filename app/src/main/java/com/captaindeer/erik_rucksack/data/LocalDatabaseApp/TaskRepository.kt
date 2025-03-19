package com.captaindeer.erik_rucksack.data.LocalDatabaseApp

import androidx.lifecycle.LiveData

/**
 * Created by suffered on 19/03/25
 */

class TaskRepository(private val taskDao: TaskDao) {

    val readAllData: LiveData<List<Task>> = taskDao.readAllData()

    suspend fun addTask(task: Task){
        taskDao.addTask(task)
    }
}