package com.shiv007.shiv007book.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {

    val books: Flow<List<BookEntity>> = dao.getAllBooks()

    suspend fun addBook(title: String, author: String, pages: Int, isRead: Boolean) {
        dao.insert(
            BookEntity(
                title = title,
                author = author,
                pages = pages,
                isRead = isRead
            )
        )
    }

    suspend fun deleteBook(book: BookEntity) = dao.delete(book)

    suspend fun clearAll() = dao.clearAll()
}
