package com.shiv007.shiv007book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.BookEntity
import com.shiv007.shiv007book.ui.theme.Shiv007BookTheme

// ------------------------------------------------------
// Navigation destinations
// ------------------------------------------------------
sealed class Screen {
    object Login : Screen()
    object Home : Screen()
    object BookList : Screen()
    object AddBook : Screen()
}

// ------------------------------------------------------
// Root app composable
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookApp(viewModel: BookViewModel) {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }
    var loggedInUser by remember { mutableStateOf<String?>(null) }

    Shiv007BookTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                Screen.Login -> LoginScreen(
                    onLoginSuccess = { username ->
                        loggedInUser = username
                        currentScreen = Screen.Home
                    }
                )

                Screen.Home -> HomeScreen(
                    username = loggedInUser.orEmpty(),
                    onViewBooks = { currentScreen = Screen.BookList },
                    onAddBook = { currentScreen = Screen.AddBook },
                    onLogout = {
                        loggedInUser = null
                        currentScreen = Screen.Login
                    }
                )

                Screen.BookList -> BookListScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Home }
                )

                Screen.AddBook -> AddBookScreen(
                    viewModel = viewModel,
                    onBack = { currentScreen = Screen.Home }
                )
            }
        }
    }
}

// ------------------------------------------------------
// Login screen (username + password)
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookly – Login") }
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
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Welcome to Bookly",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text("Login with your username and password.")

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (error != null) {
                        Text(
                            text = error!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Button(
                        onClick = {
                            if (username.isBlank() || password.length < 4) {
                                error =
                                    "Enter a username and a password of at least 4 characters."
                            } else {
                                error = null
                                onLoginSuccess(username)
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Login")
                    }
                }
            }
        }
    }
}

// ------------------------------------------------------
// Home screen
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    username: String,
    onViewBooks: () -> Unit,
    onAddBook: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bookly – Home") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(), Arrangement.spacedBy(16.dp), Alignment.CenterHorizontally
        ) {
            Text(
                text = "Hello, $username",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Text("What would you like to do today?")

            Button(
                onClick = onViewBooks,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.List, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("View My Books")
            }

            Button(
                onClick = onAddBook,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Add a New Book")
            }



        }
    }
}

// ------------------------------------------------------
// Book list screen
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookListScreen(
    viewModel: BookViewModel,
    onBack: () -> Unit
) {
    val books by viewModel.books.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Books") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {}
    ) { padding ->
        if (books.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No books yet. Add one from the Home screen.")
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
                    BookRow(
                        book = book,
                        onDelete = { viewModel.deleteBook(book) }
                    )
                }
            }
        }
    }
}

// ------------------------------------------------------
// Row for a single book
// ------------------------------------------------------
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
            Column(
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(book.title, fontWeight = FontWeight.Bold)
                Text("by ${book.author}")
                Text("${book.pages} pages")
                Text(if (book.isRead) "Finished" else "Not read yet")
            }
            IconButton(onClick = onDelete) {
                // Use Delete icon – avoids the non-existent Icons.Default.Book
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

// ------------------------------------------------------
// Add book screen
// ------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBookScreen(
    viewModel: BookViewModel,
    onBack: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var pagesText by remember { mutableStateOf("") }
    var isRead by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Book") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
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
                onValueChange = { pagesText = it },
                label = { Text("Pages") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = isRead, onCheckedChange = { isRead = it })
                Text("Already read?")
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
