package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel

@Composable
fun BookDetailScreen(
    bookId: Int,
    bookViewModel: BookViewModel,
    onBack: () -> Unit
) {
    bookViewModel.loadBook(bookId)
    val book by bookViewModel.selectedBook.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Book Detail") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        book?.let { bookViewModel.deleteBook(it) }
                        onBack()
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        book?.let {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Title: ${it.title}", style = MaterialTheme.typography.titleLarge)
                Text("Author: ${it.author}")
                Text("Pages: ${it.pages}")
                Text("Category: ${it.category}")
                Text(if (it.isRead) "Status: Finished" else "Status: Not read yet")
            }
        } ?: Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Book not found")
        }
    }
}
