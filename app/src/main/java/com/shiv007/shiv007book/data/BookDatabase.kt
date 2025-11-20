package com.shiv007.shiv007book.data

object BookDatabase {

    private val books = mutableListOf<Book>()
    private var nextId = 1

    val dao: BookDao = object : BookDao {

        override fun getAllBooks(): List<Book> = books.toList()

        override fun insertBook(book: Book): Book {
            val newBook = book.copy(id = nextId++)
            books.add(newBook)
            return newBook
        }

        override fun updateBook(book: Book) {
            val index = books.indexOfFirst { it.id == book.id }
            if (index != -1) books[index] = book
        }

        override fun deleteBook(book: Book) {
            books.removeAll { it.id == book.id }
        }

        override fun getBookById(id: Int): Book? =
            books.find { it.id == id }
    }
}
