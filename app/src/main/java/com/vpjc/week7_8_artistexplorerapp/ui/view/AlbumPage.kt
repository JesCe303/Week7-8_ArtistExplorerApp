package com.vpjc.week7_8_artistexplorerapp.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vpjc.week7_8_artistexplorerapp.ui.model.Album
import com.vpjc.week7_8_artistexplorerapp.ui.model.Track

@Composable
fun AlbumPage(
    album: Album,
    tracks: List<Track>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2B2B2B))
        ) {
            Column {
                AsyncImage(
                    model = album.coverUrl,
                    contentDescription = album.nameAlbum,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier.padding(start = 12.dp, bottom = 12.dp, end = 12.dp)
                ) {
                    Text(
                        text = album.nameAlbum,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "${album.releaseDate} • ${album.genre}",
                        color = Color(0xFFA6A07A),
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = album.description ?: "",
                        color = Color(0xFFE0E0E0),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        maxLines = 7,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Tracks",
            color = Color(0xFFA6A07A),
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        tracks.forEachIndexed { index, track ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFA6A07A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        color = Color(0xFF282828),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = track.nameTrack,
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.weight(1f)
                )

                val durationString = track.duration.toString().trim()
                val durationInt = durationString.toIntOrNull() ?: 0
                val secondsTotal = if (durationInt > 1000) durationInt / 1000 else durationInt
                val minutes = secondsTotal / 60
                val seconds = secondsTotal % 60
                val formattedDuration = "%d:%02d".format(minutes, seconds)

                Text(
                    text = formattedDuration,
                    color = Color(0xFFA6A07A),
                    fontSize = 14.sp
                )
            }

            HorizontalDivider(
                color = Color(0xFF3A3A3A),
                thickness = 1.dp
            )
        }
    }
}