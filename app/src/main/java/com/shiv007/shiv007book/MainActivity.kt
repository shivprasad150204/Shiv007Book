package com.shiv007.shiv007book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.shiv007.shiv007book.ui.*
import com.shiv007.shiv007book.ui.theme.Shiv007BookTheme

sealed class Screen {
    object List : Screen()
    data class Detail(val id: Int) : Screen()
    data class Add(val id: Int? = null) : Screen()
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Shiv007BookTheme {

                val viewModel = remember { BookViewModel() }
                var screen by remember { mutableStateOf<Screen>(Screen.List) }

                when (val s = screen) {

                    is Screen.List -> BookListScreen(
                        viewModel = viewModel,
                        onAdd = { screen = Screen.Add(null) },
                        onSelect = { id ->
                            viewModel.selectBook(id)
                            screen = Screen.Detail(id)
                        }
                    )

                    is Screen.Detail -> BookDetailScreen(
                        book = viewModel.getSelectedBook(),
                        onBack = { screen = Screen.List },
                        onEdit = { id -> screen = Screen.Add(id) },
                        onDelete = { id -> viewModel.deleteBook(id) }
                    )

                    is Screen.Add -> {
                        val existing = s.id?.let { viewModel.getSelectedBook() }
                        AddBookScreen(
                            existing = existing,
                            onSave = { title, author, pages, isRead ->
                                if (existing == null) {
                                    viewModel.addBook(title, author, pages, isRead)
                                } else {
                                    viewModel.updateBook(existing.id, title, author, pages, isRead)
                                }
                                screen = Screen.List
                            },
                            onCancel = { screen = Screen.List }
                        )
                    }
                }
            }
        }
    }
}
