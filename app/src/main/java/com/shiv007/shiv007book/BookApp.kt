package com.shiv007.shiv007book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.BookEntity

sealed class Screen {
    object Login : Screen()
    object List : Screen()
    object Add : Screen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookApp(viewModel: BookViewModel) {
    var screen by remember { mutableStateOf<Screen>(Screen.Login) }
    var username by remember { mutableStateOf("") }

    when (screen) {
        Screen.Login -> LoginScreen(
            onLogin = { name ->
                username = name
                screen = Screen.List
            }
        )

        Screen.List -> BookListScreen(
            username = username,
            viewModel = viewModel,
            onAddClick = { screen = Screen.Add },
            onLogout = { screen = Screen.Login }
        )

        Screen.Add -> AddBookScreen(
            onBack = { screen = Screen.List },
            viewModel = viewModel
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLogin: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Tracker – Login") }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Welcome",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Your name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { if (name.isNotBlank()) onLogin(name) },
                        enabled = name.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enter")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    username: String,
    viewModel: BookViewModel,
    onAddClick: () -> Unit,
    onLogout: () -> Unit
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hi $username, your books") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            if (books.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No books yet. Tap + to add one.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(books) { book ->
                        BookRow(
                            book = book,
                            onDelete = { viewModel.deleteBook(book) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BookRow(
    book: BookEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(book.title, fontWeight = FontWeight.SemiBold)
                Text(book.author, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Pages: ${book.pages}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = if (book.isRead) "Status: Finished" else "Status: Not read yet",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            TextButton(onClick = onDelete) {
                Text("Delete")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    onBack: () -> Unit,
    viewModel: BookViewModel
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var pagesText by remember { mutableStateOf("") }
    var isRead by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add book") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("< Back", color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = pagesText,
                    onValueChange = { pagesText = it.filter(Char::isDigit) },
                    label = { Text("Pages") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isRead,
                        onCheckedChange = { isRead = it }
                    )
                    Text("Mark as finished")
                }

                Button(
                    onClick = {
                        val pages = pagesText.toIntOrNull() ?: 0
                        viewModel.addBook(title, author, pages, isRead)
                        onBack()
                    },
                    enabled = title.isNotBlank() && author.isNotBlank(),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save")
                }
            }
        }
    }
}
