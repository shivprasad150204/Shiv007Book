package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.Book

@Composable
fun BookListScreen(
    bookViewModel: BookViewModel,
    onBack: () -> Unit,
    onAddBook: () -> Unit,
    onOpenBook: (Int) -> Unit
) {
    val books by bookViewModel.books.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your Books") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
                Icon(Icons.Default.Add, contentDescription = "Add book")
            }
        }
    ) { padding ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No books yet. Tap + to add one.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(books) { book ->
                    BookRow(book = book, onClick = { onOpenBook(book.id) })
                }
            }
        }
    }
}

@Composable
private fun BookRow(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text("by ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("${book.pages} pages • ${book.category}", style = MaterialTheme.typography.bodySmall)
            Text(
                if (book.isRead) "Finished" else "Not read yet",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
