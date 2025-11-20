package com.shiv007.shiv007book.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.data.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    existing: Book?,
    onSave: (String, String, Int, Boolean) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember { mutableStateOf(existing?.title ?: "") }
    var author by remember { mutableStateOf(existing?.author ?: "") }
    var pages by remember { mutableStateOf(existing?.pages?.toString() ?: "") }
    var isRead by remember { mutableStateOf(existing?.isRead ?: false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (existing == null) "Add Book" else "Edit Book")
                },
                navigationIcon = {
                    TextButton(onClick = onCancel) { Text("Back") }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
            OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("Author") })
            OutlinedTextField(value = pages, onValueChange = { pages = it }, label = { Text("Pages") })

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(checked = isRead, onCheckedChange = { isRead = it })
                Text("Mark as read")
            }

            Button(
                onClick = {
                    val p = pages.toIntOrNull()
                    if (title.isNotBlank() && p != null) {
                        onSave(title, author, p, isRead)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}

