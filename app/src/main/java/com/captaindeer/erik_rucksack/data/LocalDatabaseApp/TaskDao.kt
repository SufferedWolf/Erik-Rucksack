package com.captaindeer.erik_rucksack.data.LocalDatabaseApp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by suffered on 19/03/25
 */

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun readAllData(): LiveData<List<Task>>


}