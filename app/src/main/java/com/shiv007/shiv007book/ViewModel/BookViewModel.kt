package com.shiv007.shiv007book.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shiv007.shiv007book.data.Book
import com.shiv007.shiv007book.data.BookDatabase
import com.shiv007.shiv007book.data.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {

    private val repository =
        BookRepository(BookDatabase.getInstance(application).bookDao())

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    init {
        viewModelScope.launch {
            repository.books.collectLatest { list ->
                _books.value = list
            }
        }
    }

    fun loadBook(id: Int) {
        viewModelScope.launch {
            _selectedBook.value = repository.getBook(id)
        }
    }

    fun clearSelected() {
        _selectedBook.value = null
    }

    fun addBook(title: String, author: String, pages: Int, category: String, isRead: Boolean) {
        viewModelScope.launch {
            repository.addBook(title, author, pages, category, isRead)
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }

    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
}
