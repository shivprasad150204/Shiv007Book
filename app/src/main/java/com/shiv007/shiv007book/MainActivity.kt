package com.shiv007.shiv007book // Make sure this package name matches your project's package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shiv007.shiv007book.ui.theme.Shiv007BookTheme // Adjust this import to your theme's actual path if it's different

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Shiv007BookTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

/**
 * Defines the routes for the navigation. We've added Profile and Settings.
 */
sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object Detail : Screen("detail_screen")
    object Profile : Screen("profile_screen") // New screen
    object Settings : Screen("settings_screen") // New screen
}

/**
 * The main entry point for the app's UI, setting up the navigation graph.
 */
@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Detail.route) {
            DetailScreen(navController = navController)
        }
        // Add the new screens to the navigation graph
        composable(Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(navController = navController)
        }
    }
}

/**
 * The UI for the Home Screen, now with buttons to navigate to all other screens.
 */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Detail.route) }) {
            Text("Go to Detail Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Profile.route) }) {
            Text("Go to Profile Screen")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Settings.route) }) {
            Text("Go to Settings Screen")
        }
    }
}

/**
 * A generic TopAppBar for screens that need a back button.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(title: String, navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

/**
 * The UI for the Detail Screen, now with a back button.
 */
@Composable
fun DetailScreen(navController: NavController) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = { SimpleTopAppBar(title = "Detail", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { newText -> text = newText },
                label = { Text("Enter some text") },
                singleLine = true
            )
        }
    }
}

/**
 * NEW: The UI for the Profile Screen.
 */
@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        topBar = { SimpleTopAppBar(title = "Profile", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("This is the Profile Screen", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(16.dp))
            // Example of navigating from Profile to Settings
            Button(onClick = { navController.navigate(Screen.Settings.route) }) {
                Text("Go to Settings")
            }
        }
    }
}

/**
 * NEW: The UI for the Settings Screen.
 */
@Composable
fun SettingsScreen(navController: NavController) {
    Scaffold(
        topBar = { SimpleTopAppBar(title = "Settings", navController = navController) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("This is the Settings Screen", style = MaterialTheme.typography.bodyLarge)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    // Pop back to the previous screen
                    navController.popBackStack()
                }) {
                    Text("Go Back")
                }
                Spacer(Modifier.width(16.dp))
                Button(onClick = {
                    // Navigate to Home, clearing the back stack
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }) {
                    Text("Go to Home")
                }
            }
        }
    }
}


// --- Previews for the new screens ---

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Shiv007BookTheme {
        HomeScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    Shiv007BookTheme {
        DetailScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    Shiv007BookTheme {
        ProfileScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Shiv007BookTheme {
        SettingsScreen(navController = rememberNavController())
    }
}

