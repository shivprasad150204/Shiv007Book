Bookly – Book Tracker App (Jetpack Compose)
Project Overview

Bookly is a simple Android Book Tracker application built using Jetpack Compose and an offline Room database. The app allows users to log in, manage a personal book collection, add new books, view saved books, and toggle between light and dark mode using a settings screen.

The focus of this project is to demonstrate:

Clean UI design

Navigation across multiple screens

Offline data storage

Simple authentication logic

Use of ViewModel and Repository pattern

Features
1. Login System

Requires username and password

Allows special characters

Validates minimum password length

Stored in memory for current session only

2. Home Screen

Displays the logged-in username

Navigation buttons:

View My Books

Add New Book



Logout

3. Add Book Screen

Add books with:

Title

Author

Page count

Read status



4. Book List Screen

Displays all books stored in the database

Shows:

Title

Author

Page count

Read status

Delete a book using the delete icon


