package com.shiv007.shiv007book.ViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel() {

    var userName by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var isLoggedIn by mutableStateOf(false)
        private set

    fun updateUserName(value: String) {
        userName = value
    }

    fun updatePassword(value: String) {
        password = value
    }

    fun login() {
        // Very simple rule: anything non-blank + password length >= 4
        isLoggedIn = userName.isNotBlank() && password.length >= 4
    }

    fun logout() {
        isLoggedIn = false
        password = ""
    }
}
