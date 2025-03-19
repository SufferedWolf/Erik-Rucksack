package com.captaindeer.erik_rucksack.ui.screens

/**
 * Created by suffered on 18/03/25
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.captaindeer.erik_rucksack.core.utils.ScreensBackHandlerCustom
import com.captaindeer.erik_rucksack.data.localDatabaseApp.Task
import com.captaindeer.erik_rucksack.ui.viewmodels.TaskViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun LocalDatabaseScreen(taskViewModel: TaskViewModel = viewModel()) {
    ScreensBackHandlerCustom.DoubleBackToExit()
    var titleTextValue by remember { mutableStateOf("") }
    val taskList by taskViewModel.taskList.collectAsState()
    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
    val formattedDate = date.format(formatter)

    Column {
        Text(text = "Hello Local Database Screen")

        OutlinedTextField(
            value = titleTextValue,
            onValueChange = { titleTextValue = it },
            label = { Text("Task Title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (titleTextValue.isNotBlank()) {
                    val newTask = Task(
                        title = titleTextValue,
                        done = false,
                        createdDate = formattedDate
                    )
                    taskViewModel.addTask(newTask)
                    titleTextValue = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        LazyColumn {
            items(taskList) { task ->
                TaskItem(task = task)
                Button(onClick = {
                    taskViewModel.deleteTask(task)
                }) { Text("Delete") }
            }
        }
    }
}
@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = task.title,
            modifier = Modifier.padding(16.dp)
        )
    }
}
