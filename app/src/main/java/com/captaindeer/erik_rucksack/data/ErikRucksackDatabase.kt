package com.captaindeer.erik_rucksack.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.captaindeer.erik_rucksack.data.localDatabaseApp.Task
import com.captaindeer.erik_rucksack.data.localDatabaseApp.TaskDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

/**
 * Created by suffered on 19/03/25
 */

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class ErikRucksackDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: ErikRucksackDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): ErikRucksackDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ErikRucksackDatabase::class.java,
                    "erik_rucksack_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}