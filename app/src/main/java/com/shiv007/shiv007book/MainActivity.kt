package com.shiv007.shiv007book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.shiv007.shiv007book.ViewModel.AuthViewModel
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.ViewModel.BookViewModelFactory
import com.shiv007.shiv007book.ui.theme.*

sealed class Screen {
    object Login : Screen()
    object Home : Screen()
    object List : Screen()
    object Add : Screen()
    data class Detail(val bookId: Int) : Screen()
    object Account : Screen()
}

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Shiv007BookTheme {
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

                when (val screen = currentScreen) {
                    Screen.Login -> LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = { currentScreen = Screen.Home }
                    )

                    Screen.Home -> HomeScreen(
                        onViewBooks = { currentScreen = Screen.List },
                        onAddBook = { currentScreen = Screen.Add },
                        onAccount = { currentScreen = Screen.Account }
                    )

                    Screen.List -> BookListScreen(
                        bookViewModel = bookViewModel,
                        onBack = { currentScreen = Screen.Home },
                        onAddBook = { currentScreen = Screen.Add },
                        onOpenBook = { id -> currentScreen = Screen.Detail(id) }
                    )

                    Screen.Add -> AddBookScreen(
                        bookViewModel = bookViewModel,
                        existingBook = null,
                        onDone = { currentScreen = Screen.List }
                    )

                    is Screen.Detail -> BookDetailScreen(
                        bookId = screen.bookId,
                        bookViewModel = bookViewModel,
                        onBack = { currentScreen = Screen.List }
                    )

                    Screen.Account -> AccountScreen(
                        authViewModel = authViewModel,
                        onBack = { currentScreen = Screen.Home },
                        onLogout = { currentScreen = Screen.Login }
                    )
                }
            }
        }
    }
}
