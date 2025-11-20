package com.shiv007.shiv007book.data

data class Book(
    val id: Int = 0,
    val title: String,
    val author: String,
    val pages: Int,
    val isRead: Boolean = false
)
