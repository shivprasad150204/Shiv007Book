package com.shiv007.shiv007book.data

interface BookDao {
    fun getAllBooks(): List<Book>
    fun insertBook(book: Book): Book
    fun updateBook(book: Book)
    fun deleteBook(book: Book)
    fun getBookById(id: Int): Book?
}
