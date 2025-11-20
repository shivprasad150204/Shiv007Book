package com.shiv007.shiv007book.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    bookViewModel: BookViewModel,
    onAddBook: () -> Unit,
    onBookSelected: (Int) -> Unit,
    onLogout: () -> Unit
) {
    val books = bookViewModel.books

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Books") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddBook) {
                Text("+")
            }
        }
    ) { padding ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No books yet. Tap + to add.")
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
                    BookListItem(book = book, onClick = { onBookSelected(book.id) })
                }
            }
        }
    }
}

@Composable
private fun BookListItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Text("by ${book.author}", style = MaterialTheme.typography.bodyMedium)
            Text("${book.pages} pages", style = MaterialTheme.typography.bodySmall)
            Text(
                if (book.isRead) "Finished" else "Not read yet",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

