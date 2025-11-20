package com.shiv007.shiv007book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.shiv007.shiv007book.ViewModel.AuthViewModel
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.data.Book
import com.shiv007.shiv007book.ui.theme.*

/** Simple navigation states for the app */
sealed class Screen {
    object Login : Screen()
    object List : Screen()
    data class Detail(val bookId: Int) : Screen()
    data class AddOrEdit(val bookId: Int? = null) : Screen()
}

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Shiv007BookTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

                when (val screen = currentScreen) {

                    Screen.Login -> LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = { currentScreen = Screen.List }
                    )

                    Screen.List -> BookListScreen(
                        bookViewModel = bookViewModel,
                        onAddBook = { currentScreen = Screen.AddOrEdit(null) },
                        onBookSelected = { id ->
                            bookViewModel.selectBook(id)
                            currentScreen = Screen.Detail(id)
                        },
                        onLogout = {
                            authViewModel.logout()
                            currentScreen = Screen.Login
                        }
                    )

                    is Screen.Detail -> {
                        val book: Book? =
                            bookViewModel.books.find { it.id == screen.bookId }
                        BookDetailScreen(
                            book = book,
                            onBack = { currentScreen = Screen.List },
                            onEdit = { id -> currentScreen = Screen.AddOrEdit(id) },
                            onDelete = { id -> bookViewModel.deleteBook(id) }
                        )
                    }

                    is Screen.AddOrEdit -> {
                        val existing =
                            screen.bookId?.let { id -> bookViewModel.books.find { it.id == id } }
                        AddBookScreen(
                            existingBook = existing,
                            onSave = { title, author, pages, isRead ->
                                if (existing == null) {
                                    bookViewModel.addBook(title, author, pages, isRead)
                                } else {
                                    bookViewModel.updateBook(
                                        id = existing.id,
                                        title = title,
                                        author = author,
                                        pages = pages,
                                        isRead = isRead
                                    )
                                }
                                currentScreen = Screen.List
                            },
                            onCancel = { currentScreen = Screen.List }
                        )
                    }
                }
            }
        }
    }
}
