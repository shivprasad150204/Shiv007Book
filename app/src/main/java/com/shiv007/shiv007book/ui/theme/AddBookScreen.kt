package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.Book

@Composable
fun AddBookScreen(
    bookViewModel: BookViewModel,
    existingBook: Book? = null,
    onDone: () -> Unit
) {
    var title by remember { mutableStateOf(existingBook?.title ?: "") }
    var author by remember { mutableStateOf(existingBook?.author ?: "") }
    var pages by remember { mutableStateOf(existingBook?.pages?.toString() ?: "") }
    var category by remember { mutableStateOf(existingBook?.category ?: "") }
    var isRead by remember { mutableStateOf(existingBook?.isRead ?: false) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (existingBook == null) "Add Book" else "Edit Book") },
                navigationIcon = {
                    IconButton(onClick = onDone) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
                value = pages,
                onValueChange = { pages = it },
                label = { Text("Pages") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Checkbox(checked = isRead, onCheckedChange = { isRead = it })
                Text("Mark as read")
            }

            if (showError) {
                Text(
                    "Please enter a title and valid number of pages.",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    val pagesInt = pages.toIntOrNull()
                    if (title.isBlank() || pagesInt == null) {
                        showError = true
                    } else {
                        showError = false
                        if (existingBook == null) {
                            bookViewModel.addBook(title, author, pagesInt, category, isRead)
                        } else {
                            bookViewModel.updateBook(
                                existingBook.copy(
                                    title = title,
                                    author = author,
                                    pages = pagesInt,
                                    category = category,
                                    isRead = isRead
                                )
                            )
                        }
                        onDone()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }
        }
    }
}
