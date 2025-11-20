package com.shiv007.shiv007book.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.shiv007.shiv007book.data.Book
import com.shiv007.shiv007book.data.BookRepository

class BookViewModel(
    private val repo: BookRepository = BookRepository()
) : ViewModel() {

    val books = repo.books

    var selectedBookId by mutableStateOf<Int?>(null)
        private set

    fun selectBook(id: Int) {
        selectedBookId = id
    }

    fun getSelectedBook(): Book? =
        selectedBookId?.let { repo.getBook(it) }

    fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        repo.addBook(title, author, pages, isRead)
    }

    fun updateBook(id: Int, title: String, author: String, pages: Int, isRead: Boolean) {
        repo.updateBook(id, title, author, pages, isRead)
    }

    fun deleteBook(id: Int) {
        repo.deleteBook(id)
    }
}


