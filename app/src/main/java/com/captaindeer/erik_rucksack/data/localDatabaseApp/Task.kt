package com.captaindeer.erik_rucksack.data.localDatabaseApp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val done: Boolean = false,
    val createdDate: String = ""
)
