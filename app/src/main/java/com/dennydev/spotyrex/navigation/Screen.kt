package com.dennydev.spotyrex.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val unselectedIcon: ImageVector, val selectedIcon: ImageVector, val route: String, val title: String) {
    object LoginScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Login", "Login")
    object HomeScreen: Screen(Icons.Default.Home, Icons.Default.Home, "Home", "Home")
    object ProfileScreen: Screen(Icons.Default.Person, Icons.Default.Person, "Profile", "Profile")
    object SettingScreen: Screen(Icons.Default.Settings,Icons.Default.Settings,"Settings", "Settings")
    object PlaylistScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Playlist/{playlistId}", "Playlist")
    object ArtistScreen: Screen(Icons.Default.Check, Icons.Default.Check, "Artist/{artistId}", "Artist")
    object PlayerScreen: Screen(Icons.Default.Check, Icons.Default.Check, "PlayerScreen", "Player")
}