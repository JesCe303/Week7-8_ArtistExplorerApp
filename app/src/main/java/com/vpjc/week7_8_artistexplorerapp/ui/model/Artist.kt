package com.vpjc.week7_8_artistexplorerapp.ui.model

data class Artist(
    val nameArtist: String = "",
    val idArtist: Int = 0,
    val genre: String = "",
    val coverUrl: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)