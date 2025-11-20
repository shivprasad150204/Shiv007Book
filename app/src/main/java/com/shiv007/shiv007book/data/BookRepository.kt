package com.shiv007.shiv007book.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

/**
 * Very simple in-memory repository.
 * No Room, no Firebase. Survives only while the app is running.
 */
class BookRepository {

    private val _books = mutableStateListOf<Book>()
    val books: SnapshotStateList<Book> get() = _books

    private var nextId = 1

    fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        val book = Book(
            id = nextId++,
            title = title,
            author = author,
            pages = pages,
            isRead = isRead
        )
        _books.add(book)
    }

    fun updateBook(id: Int, title: String, author: String, pages: Int, isRead: Boolean) {
        val index = _books.indexOfFirst { it.id == id }
        if (index >= 0) {
            _books[index] = Book(id, title, author, pages, isRead)
        }
    }

    fun deleteBook(id: Int) {
        _books.removeAll { it.id == id }
    }

    fun getBookById(id: Int): Book? = _books.find { it.id == id }
}
