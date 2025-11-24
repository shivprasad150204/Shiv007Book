package com.shiv007.shiv007book.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val pages: Int,
    val category: String,
    val isRead: Boolean
)
