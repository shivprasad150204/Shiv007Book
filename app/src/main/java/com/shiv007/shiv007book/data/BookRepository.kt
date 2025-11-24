package com.shiv007.shiv007book.data

import kotlinx.coroutines.flow.Flow

class BookRepository(
    private val dao: BookDao
) {

    val books: Flow<List<BookEntity>> = dao.getAllBooks()

    suspend fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        val book = BookEntity(
            title = title,
            author = author,
            pages = pages,
            isRead = isRead
        )
        dao.insert(book)
    }

    suspend fun deleteBook(book: BookEntity) {
        dao.delete(book)
    }

    suspend fun clearAll() {
        dao.clearAll()
    }
}
