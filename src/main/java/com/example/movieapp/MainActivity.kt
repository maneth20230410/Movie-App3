package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.movieapp.ui.screens.ActorSearchScreen
import com.example.movieapp.ui.screens.HomeScreen
import com.example.movieapp.ui.screens.MovieSearchScreen
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
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
fun AddMoviesToDBScreen(onNavigateBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Add Movies to Database",
            style = MaterialTheme.typography.h5
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Implementation of saving movies to database
        // This would include fetching movies from the web service
        // and saving them to the Room database

        Button(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Back")
        }
    }
}