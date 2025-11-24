package com.shiv007.shiv007book.data

import kotlinx.coroutines.flow.Flow

class BookRepository(private val dao: BookDao) {

    val books: Flow<List<Book>> = dao.getAllBooks()

    suspend fun addBook(
        title: String,
        author: String,
        pages: Int,
        category: String,
        isRead: Boolean
    ) {
        val book = Book(
            title = title,
            author = author,
            pages = pages,
            category = category,
            isRead = isRead
        )
        dao.insertBook(book)
    }

    suspend fun updateBook(book: Book) = dao.updateBook(book)

    suspend fun deleteBook(book: Book) = dao.deleteBook(book)

    suspend fun getBook(id: Int): Book? = dao.getBookById(id)
}
