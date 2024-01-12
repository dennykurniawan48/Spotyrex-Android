package com.dennydev.spotyrex.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.components.BottomNav
import com.dennydev.spotyrex.components.items
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val selectedBottom by mainViewModel.selecteBottomNav
    val token by mainViewModel.token.collectAsState(initial = "")
    val playlist by mainViewModel.homePlaylist

    val playerState by mainViewModel.playerState.collectAsState()

    LaunchedEffect(key1 = token) {
        if (playlist !is ApiResponse.Success) {
            mainViewModel.getPlaylist()
        }
    }

    Scaffold(bottomBar = {
            BottomNav(
                navController = navController,
                selectedBottom = selectedBottom,
                song=playerState.currentAudio,
                onChangeSelectedBottomIndex = { route ->
                    mainViewModel.onChangeSelectedBottomNav(route)
                })
    }, topBar = {
        CenterAlignedTopAppBar(title = { Text("Home", fontWeight = FontWeight.Bold) })
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            playlist.data?.data?.let {
                items(it) {
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp), onClick = {
                        navController.navigate(
                            Screen.PlaylistScreen.route.replace(
                                "{playlistId}",
                                it._id
                            )
                        )
                    }) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.image),
                                contentDescription = "Image",
                                contentScale = ContentScale.FillWidth,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(it.name, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                "${it.songs.size} songs",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Prevhome() {
    Scaffold(bottomBar = {
        Column(modifier= Modifier.fillMaxWidth()) {
            Row(modifier=Modifier.padding(vertical=8.dp, horizontal = 12.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.music),
                    contentDescription = "song art",
                    modifier = Modifier.size(40.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier= Modifier
                    .height(40.dp)
                    .fillMaxWidth(), verticalArrangement = Arrangement.Center){
                    Text("Song", style = MaterialTheme.typography.titleMedium)
                    Text("Artist", style = MaterialTheme.typography.bodySmall)
                }
                Spacer(modifier = Modifier.width(12.dp))
            }
            NavigationBar {
                items.forEachIndexed { index, item ->

                    NavigationBarItem(
                        selected = true,
                        onClick = {

                        },
                        label = {
                            Text(text = item.title)
                        },
                        alwaysShowLabel = false,
                        icon = {
                            Icon(
                                imageVector = if (true) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        }
                    )
                }
            }
        }
    }, topBar = {
        CenterAlignedTopAppBar(title = { Text("Home", fontWeight = FontWeight.Bold) })
    }) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            items(5) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.music),
                            contentDescription = "Image",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Fall in Love", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("16 songs", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}