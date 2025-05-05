package com.example.movieapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.local.entities.MovieEntity
import com.example.movieapp.viewmodels.MovieSearchViewModel

@Composable
fun MovieSearchScreen(
    onNavigateBack: () -> Unit,
    viewModel: MovieSearchViewModel = viewModel()
) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.searchResults.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        Button(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text(text = "Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Search for Movies",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Search Input Field
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter movie title") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { viewModel.searchMovies(searchQuery) }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search Button
        Button(
            onClick = { viewModel.searchMovies(searchQuery) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // Results
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(searchResults) { movie ->
                MovieItem(movie = movie) {
                    // When clicked, save to DB
                    viewModel.saveMovieToDatabase(movie)
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: MovieEntity, onSaveClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.h6,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Year: ${movie.year}",
                style = MaterialTheme.typography.body2
            )

            Text(
                text = "Director: ${movie.director ?: "Unknown"}",
                style = MaterialTheme.typography.body2
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onSaveClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Save to Database")
            }
        }
    }
}