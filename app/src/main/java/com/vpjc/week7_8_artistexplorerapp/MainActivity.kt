package com.vpjc.week7_8_artistexplorerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vpjc.week7_8_artistexplorerapp.ui.theme.Week78_ArtistExplorerAppTheme
import com.vpjc.week7_8_artistexplorerapp.ui.route.AppRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week78_ArtistExplorerAppTheme {
                AppRoute()
            }
        }
    }
}
