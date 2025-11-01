package com.vpjc.week7_8_artistexplorerapp.ui.model

data class Track(
    val nameTrack: String = "",
    val idTrack: Int = 0,
    val duration: Int = 0,
    val isError: Boolean = false,
    val errorMessage: String? = null
)