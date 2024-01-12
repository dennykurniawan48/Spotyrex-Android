package com.dennydev.spotyrex.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dennydev.spotyrex.screen.ArtistScreen
import com.dennydev.spotyrex.screen.HomeScreen
import com.dennydev.spotyrex.screen.LoginScreen
import com.dennydev.spotyrex.screen.PlayerScreen
import com.dennydev.spotyrex.screen.PlaylistScreen
import com.dennydev.spotyrex.screen.ProfileScreen
import com.dennydev.spotyrex.viewmodel.MainViewModel

@Composable
fun SetupNavigation(navController: NavHostController, startDestination: String) {
    val mainViewModel: MainViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier
    ) {
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController)
        }
        composable(Screen.HomeScreen.route){
            HomeScreen(navController = navController, mainViewModel=mainViewModel)
        }
        composable(Screen.ProfileScreen.route){
            ProfileScreen(navController = navController, mainViewModel=mainViewModel)
        }
        composable(Screen.PlaylistScreen.route, arguments = listOf(
            navArgument(name="playlistId"){
                type = NavType.StringType
            })){
            it.arguments?.getString("playlistId")?.let { playlistId ->
                PlaylistScreen(navController = navController, playlistId = playlistId, mainViewModel = mainViewModel)
            }
        }
        composable(Screen.PlayerScreen.route){
            PlayerScreen(navController = navController, mainViewModel = mainViewModel)
        }
        composable(Screen.ArtistScreen.route, arguments = listOf(
            navArgument(name="artistId"){
                type = NavType.StringType
            })){
            it.arguments?.getString("artistId")?.let { artistId ->
                ArtistScreen(navController = navController, mainViewModel = mainViewModel, artistId = artistId)
            }
        }
    }
}