package com.shiv007.shiv007book.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shiv007.shiv007book.data.BookDatabase
import com.shiv007.shiv007book.data.BookEntity
import com.shiv007.shiv007book.data.BookRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: BookRepository

    val books: StateFlow<List<BookEntity>>

    init {
        val db = BookDatabase.getInstance(application)
        repository = BookRepository(db.bookDao())

        books = repository.books
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList()
            )
    }

    fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        viewModelScope.launch {
            repository.addBook(title, author, pages, isRead)
        }
    }

    fun deleteBook(book: BookEntity) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}
