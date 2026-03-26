package com.example.roomtask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var appDb: AppDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDb = Room.databaseBuilder(
            applicationContext,
            AppDb::class.java,
            "app_database"
        ).build()

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TaskScreen(appDb.dao())
                }
            }
        }
    }
}

@Composable
fun TaskScreen(taskDao: TaskDao) {
    val scope = rememberCoroutineScope()
    val newTaskTitle = remember { mutableStateOf("") }
    val tasks = taskDao.getAll().collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("📝 Room Task Manager", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = newTaskTitle.value,
                onValueChange = { newTaskTitle.value = it },
                label = { Text("Nouvelle tâche") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Button(
                onClick = {
                    if (newTaskTitle.value.isNotBlank()) {
                        scope.launch {
                            taskDao.insert(Task(title = newTaskTitle.value))
                            newTaskTitle.value = ""
                        }
                    }
                }
            ) {
                Text("Ajouter")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("${tasks.value.size} tâche(s)", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(tasks.value) { task ->
                TaskItem(task, taskDao, scope)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, taskDao: TaskDao, scope: kotlinx.coroutines.CoroutineScope) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("✓ ${task.title}", modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                scope.launch {
                    taskDao.delete(task)
                }
            }
        ) {
            Text("🗑️")
        }
    }
}
