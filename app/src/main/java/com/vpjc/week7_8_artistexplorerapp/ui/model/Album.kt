package com.vpjc.week7_8_artistexplorerapp.ui.model

data class Album(
    val nameAlbum: String = "",
    val idAlbum: Int = 0,
    val releaseDate: String = "",
    val coverUrl: String = "",
    val artistId: Int = 0,
    val genre: String = "",
    val description: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)