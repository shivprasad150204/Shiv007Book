package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.data.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    book: Book?,
    onBack: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Book Detail") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { padding ->
        if (book == null) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Book not found")
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Title: ${book.title}", style = MaterialTheme.typography.titleLarge)
                Text("Author: ${book.author}")
                Text("Pages: ${book.pages}")
                Text(if (book.isRead) "Status: Finished" else "Status: Not read yet")

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onEdit(book.id) }) {
                        Text("Edit")
                    }
                    OutlinedButton(onClick = { onDelete(book.id); onBack() }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

