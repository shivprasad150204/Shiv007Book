package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.data.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    existingBook: Book?,
    onSave: (title: String, author: String, pages: Int, isRead: Boolean) -> Unit,
    onCancel: () -> Unit
) {
    var title by remember(existingBook) { mutableStateOf(existingBook?.title.orEmpty()) }
    var author by remember(existingBook) { mutableStateOf(existingBook?.author.orEmpty()) }
    var pagesText by remember(existingBook) {
        mutableStateOf(existingBook?.pages?.toString().orEmpty())
    }
    var isRead by remember(existingBook) { mutableStateOf(existingBook?.isRead ?: false) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (existingBook == null) "Add Book" else "Edit Book")
                },
                navigationIcon = {
                    TextButton(onClick = onCancel) {
                        Text("Back")
                    }
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

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Author") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = pagesText,
                onValueChange = { pagesText = it },
                label = { Text("Pages") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isRead,
                    onCheckedChange = { isRead = it }
                )
                Text("Mark as read")
            }

            if (showError) {
                Text(
                    text = "Please enter a title and a valid page number",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Button(
                onClick = {
                    val pages = pagesText.toIntOrNull()
                    if (title.isBlank() || pages == null) {
                        showError = true
                    } else {
                        showError = false
                        onSave(title, author, pages, isRead)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
