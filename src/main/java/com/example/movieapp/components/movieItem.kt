package com.example.movieapp.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.movieapp.data.local.entities.MovieEntity
import coil.compose.AsyncImage

@Composable
fun MovieItem(
    movie: MovieEntity,
    onSaveClick: () -> Unit = {},
    onItemClick: (MovieEntity) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = 4.dp,
        onClick = { onItemClick(movie) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Movie Poster
            if (!movie.poster.isNullOrBlank() && movie.poster != "N/A") {
                AsyncImage(
                    model = movie.poster,
                    contentDescription = "${movie.title} poster",
                    modifier = Modifier
                        .width(80.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            // Movie Details
            Column(
                modifier = Modifier.weight(1f)
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

                movie.director?.let {
                    if (it.isNotBlank() && it != "N/A") {
                        Text(
                            text = "Director: $it",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                movie.actors?.let {
                    if (it.isNotBlank() && it != "N/A") {
                        Text(
                            text = "Actors: $it",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                movie.genre?.let {
                    if (it.isNotBlank() && it != "N/A") {
                        Text(
                            text = "Genre: $it",
                            style = MaterialTheme.typography.body2,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                movie.imdbRating?.let {
                    if (it.isNotBlank() && it != "N/A") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = MaterialTheme.colors.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = it,
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }

            // Save Button Column
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = onSaveClick,
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    }
}

@Composable
fun MovieListItem(
    movie: MovieEntity,
    onItemClick: (MovieEntity) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp,
        onClick = { onItemClick(movie) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Compact list item with just title and year
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.subtitle1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = movie.year,
                    style = MaterialTheme.typography.caption
                )
            }

            // Rating if available
            movie.imdbRating?.let {
                if (it.isNotBlank() && it != "N/A") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }
}

