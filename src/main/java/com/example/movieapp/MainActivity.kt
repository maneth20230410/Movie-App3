package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.screens.ActorSearchScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.MovieSearchScreen
import com.example.movieapp.ui.theme.theme.MovieAppTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodels.AddMoviesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentScreen by remember { mutableStateOf("home") }

                    when (currentScreen) {
                        "home" -> HomeScreen(
                            onNavigateToMovieSearch = { currentScreen = "movieSearch" },
                            onNavigateToActorSearch = { currentScreen = "actorSearch" },
                            onNavigateToAddMovies = { currentScreen = "addMovies" }
                        )
                        "movieSearch" -> MovieSearchScreen(
                            onNavigateBack = { currentScreen = "home" }
                        )
                        "actorSearch" -> ActorSearchScreen(
                            onNavigateBack = { currentScreen = "home" }
                        )
                        "addMovies" -> AddMoviesToDBScreen(
                            onNavigateBack = { currentScreen = "home" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddMoviesToDBScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddMoviesViewModel = viewModel()
) {
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val message by viewModel.message.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Movies to Database",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Input Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter movie title") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Button
        Button(
            onClick = { viewModel.searchMovie(searchQuery) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }

        // Loading Indicator or Message
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp)
            )
        } else {
            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Search Results
        if (searchResults.isNotEmpty()) {
            searchResults.forEach { movie ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = movie.title,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "Year: ${movie.year}",
                            style = MaterialTheme.typography.bodyMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { viewModel.saveMovie(movie) },
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(text = "Save to Database")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Back Button
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Back")
        }
    }
}
