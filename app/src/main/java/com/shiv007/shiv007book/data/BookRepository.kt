package com.shiv007.shiv007book.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class BookRepository(
    private val dao: BookDao = BookDatabase.dao
) {

    private val _books = mutableStateListOf<Book>().apply {
        addAll(dao.getAllBooks())
    }
    val books: SnapshotStateList<Book> = _books

    fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        val newBook = dao.insertBook(
            Book(title = title, author = author, pages = pages, isRead = isRead)
        )
        _books.add(newBook)
    }

    fun updateBook(id: Int, title: String, author: String, pages: Int, isRead: Boolean) {
        val updated = Book(id, title, author, pages, isRead)
        dao.updateBook(updated)
        val index = _books.indexOfFirst { it.id == id }
        if (index >= 0) _books[index] = updated
    }

    fun deleteBook(id: Int) {
        dao.getBookById(id)?.let { book ->
            dao.deleteBook(book)
            _books.removeAll { it.id == id }
        }
    }

    fun getBook(id: Int): Book? = dao.getBookById(id)
}
