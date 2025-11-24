package com.shiv007.shiv007book

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.shiv007.shiv007book.ViewModel.BookViewModel
import com.shiv007.shiv007book.ui.theme.Shiv007BookTheme

class MainActivity : ComponentActivity() {

    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Shiv007BookTheme {
                BookApp(bookViewModel)
            }
        }
    }
}
