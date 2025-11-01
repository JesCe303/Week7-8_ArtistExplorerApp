package com.vpjc.week7_8_artistexplorerapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpjc.week7_8_artistexplorerapp.data.container.ArtistArtistContainer
import com.vpjc.week7_8_artistexplorerapp.ui.model.Artist
import com.vpjc.week7_8_artistexplorerapp.ui.model.Album
import com.vpjc.week7_8_artistexplorerapp.ui.model.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArtistArtistViewModel : ViewModel() {

    private val _artist = MutableStateFlow(Artist())
    val artist: StateFlow<Artist> = _artist

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _tracks = MutableStateFlow<List<Track>>(emptyList())
    val tracks: StateFlow<List<Track>> = _tracks

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val repository = ArtistArtistContainer().artistArtistRepository

    fun loadArtist(artistName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val artistResult = repository.getArtist(artistName)
                _artist.value = artistResult
                loadAlbums(artistName)
            } catch (e: Exception) {
                _artist.value = Artist(
                    isError = true,
                    errorMessage = e.message ?: "Failed to load artist."
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun loadAlbums(artistName: String) {
        viewModelScope.launch {
            try {
                val albumResults = repository.getAlbums(artistName)
                _albums.value = albumResults
            } catch (e: Exception) {
                _albums.value = emptyList()
            }
        }
    }

    fun loadTracks(albumId: Int) {
        viewModelScope.launch {
            try {
                val trackResults = repository.getTracks(albumId)
                _tracks.value = trackResults
            } catch (e: Exception) {
                _tracks.value = listOf(
                    Track(isError = true, errorMessage = e.message ?: "Failed to load tracks.")
                )
            }
        }
    }
}