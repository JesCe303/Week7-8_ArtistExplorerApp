package com.vpjc.week7_8_artistexplorerapp.ui.route

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vpjc.week7_8_artistexplorerapp.ui.view.AlbumPage
import com.vpjc.week7_8_artistexplorerapp.ui.view.ErrorPage
import com.vpjc.week7_8_artistexplorerapp.ui.view.HomePage
import com.vpjc.week7_8_artistexplorerapp.ui.view.LoadingPage
import com.vpjc.week7_8_artistexplorerapp.ui.viewmodel.ArtistArtistViewModel

enum class AppView(
    val title: String,
    val icon: ImageVector? = null
) {
    Home("Home"),
    AlbumDetail("Album Detail"),
    Loading("Loading"),
    Error("Error")
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoute() {
    val navController = rememberNavController()
    val artistViewModel: ArtistArtistViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    val currentView = AppView.entries.find { it.name == currentRoute }

    Scaffold(
        topBar = {
            MyTopAppBar(
                currentView = currentView,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                viewModel = artistViewModel
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            startDestination = AppView.Home.name
        ) {
            composable(route = AppView.Home.name) {
                HomePage(
                    navController = navController,
                    viewModel = artistViewModel
                )
            }

            composable("${AppView.AlbumDetail.name}/{albumId}") { backStackEntry ->
                val albumId = backStackEntry.arguments?.getString("albumId")?.toIntOrNull()
                val albums by artistViewModel.albums.collectAsState()
                val tracks by artistViewModel.tracks.collectAsState()
                val selectedAlbum = albums.find { it.idAlbum == albumId }

                LaunchedEffect(albumId) {
                    albumId?.let { artistViewModel.loadTracks(it) }
                }

                if (selectedAlbum != null) {
                    AlbumPage(album = selectedAlbum, tracks = tracks)
                } else {
                    ErrorPage(message = "Album not found.")
                }
            }

            composable(route = AppView.Loading.name) {
                LoadingPage()
            }

            composable(route = AppView.Error.name) {
                ErrorPage()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentView: AppView?,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ArtistArtistViewModel
) {
    val artist by viewModel.artist.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    CenterAlignedTopAppBar(
        title = {
            Text(
                if (isLoading)
                    "Loading..."
                else if (artist.isError)
                    "Error"
                else
                    artist.nameArtist
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFF1C2021),
            titleContentColor = Color(0xFFB5B5B3)
        ),
        modifier = modifier
    )
}
