package com.example.myapplication.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.dp.DatabaseHelper
import com.example.myapplication.ui.theme.Purple40
import com.example.myapplication.ui.theme.Purple80
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import com.example.myapplication.dp.Task

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun TasksScreen() {

    var showDialog by remember { mutableStateOf(false) }
    val myDatabase = DatabaseHelper(LocalContext.current)
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(containerColor = Purple40),
                title = { Text(text = "Tasks")},
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }

                },

            )
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = Purple40,onClick = {
                showDialog = true
            }) {
                Icon(imageVector = Icons.Filled.Add,tint = Purple80,contentDescription = "Add")
            }
        }

    ){ paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (showDialog) {
                InputDialog(
                    title = "Enter Task Details",
                    initialFirstValue = "",
                    initialSecondValue = "",
                    onConfirm = { newValue1, newValue2 ->
                        myDatabase.insertTaskData(Task(id = 0,title = newValue1, content = newValue2))
                        showDialog = false
                    },
                    onCancel = { showDialog = false }
                )
            }
            LazyColumn{
                items(myDatabase.getAllTasks()){
                    ExpandableCard(title = it.title, description = it.content)
                }
            }
        }
    }
}@Composable
fun InputDialog(
    title: String,
    initialFirstValue: String = "",
    initialSecondValue: String = "",
    onConfirm: (String, String) -> Unit,
    onCancel: () -> Unit
) {
    var firstValue by remember { mutableStateOf(initialFirstValue) }
    var secondValue by remember { mutableStateOf(initialSecondValue) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = title, color = Color.Black) },
        confirmButton = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) {
                    Text(text = "Cancel", color = Color.Black)
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { onConfirm(firstValue, secondValue) }) {
                    Text(text = "OK", color = Color.Black)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = firstValue,
                    onValueChange = { firstValue = it },
                    placeholder = { Text("Enter first value") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                TextField(
                    value = secondValue,
                    onValueChange = { secondValue = it },
                    placeholder = { Text("Enter second value") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
    )
}
