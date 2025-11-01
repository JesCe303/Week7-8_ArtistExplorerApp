package com.vpjc.week7_8_artistexplorerapp.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.vpjc.week7_8_artistexplorerapp.ui.route.AppView
import com.vpjc.week7_8_artistexplorerapp.ui.viewmodel.ArtistArtistViewModel
import kotlin.math.ceil

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: ArtistArtistViewModel,
    navController: NavController = rememberNavController()
) {
    val artist by viewModel.artist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val albums by viewModel.albums.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadArtist("Radiohead")
    }

    when {
        isLoading -> LoadingPage()
        artist.isError -> ErrorPage(
            message = artist.errorMessage ?: "An unknown error occurred.",
            onRetry = { viewModel.loadArtist("Radiohead") }
        )

        else -> {
            val albumCount = albums.size
            val columns = 2
            val heightPerCard = 220.dp
            val spacing = 12.dp
            val rows = ceil(albumCount / columns.toFloat()).toInt()
            val totalHeight: Dp = (heightPerCard * rows) + (spacing * (rows - 1))

            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color(0xFF282828))
            ) {
                item {
                    Row(
                        modifier = modifier
                            .padding(top = 8.dp, bottom = 12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box {
                            Image(
                                painter = rememberAsyncImagePainter(artist.coverUrl),
                                contentDescription = "Cover",
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .border(
                                        width = 2.dp,
                                        color = Color(0xFF303030),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .size(340.dp)
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.BottomStart)
                                    .padding(12.dp)
                            ) {
                                Text(
                                    artist.nameArtist,
                                    color = Color(0xFFA6A07A),
                                    fontSize = 21.sp,
                                )
                                Text(
                                    artist.genre,
                                    color = Color(0xFFA6A07A),
                                    fontSize = 15.sp,
                                )
                            }
                        }
                    }

                    Column(
                        modifier = modifier
                            .padding(start = 16.dp, end = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Album",
                            color = Color(0xFFA6A07A)
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            userScrollEnabled = false,
                            modifier = modifier.height(totalHeight)
                        ) {
                            items(count = albums.size) { index ->
                                AlbumCard(
                                    album = albums[index],
                                    modifier = modifier,
                                    onClick = {
                                        navController.navigate("${AppView.AlbumDetail.name}/${albums[index].idAlbum}")
                                    }
                                )
                            }
                        }

                    }
                }
            }
        }
    }
}