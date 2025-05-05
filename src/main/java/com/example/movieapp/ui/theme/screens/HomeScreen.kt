package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onNavigateToMovieSearch: () -> Unit,
    onNavigateToActorSearch: () -> Unit,
    onNavigateToAddMovies: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Movie Database App",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Add Movies to DB Button
            Button(
                onClick = onNavigateToAddMovies,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Add Movies to DB",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Search for Movies Button
            Button(
                onClick = onNavigateToMovieSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Search for Movies",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Search for Actors Button
            Button(
                onClick = onNavigateToActorSearch,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Search for Actors",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}