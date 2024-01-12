package com.dennydev.spotyrex.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.viewmodel.MainViewModel
import com.dennydev.spotyrex.viewmodel.PlaylistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaylistScreen(
    navController: NavHostController,
    playlistId: String,
    viewModel: PlaylistViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val playlist by viewModel.playlistResponse

    LaunchedEffect(key1 = playlistId){
        viewModel.getDetailPlaylist(playlistId)
    }

    Scaffold(topBar = { TopAppBar(title = { }, navigationIcon = {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
        }
    }) }){
        playlist.data?.data?.let { dataPlaylist->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(24.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = dataPlaylist.image),
                            contentDescription = "artist",
                            modifier = Modifier
                                .fillMaxWidth(0.25f)
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = dataPlaylist.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = { mainViewModel.convertPlaylistResponseToPlaylist(dataPlaylist, true, 0) }) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircleOutline,
                                    contentDescription = ""
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "All Songs",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                items(dataPlaylist.songs.size) { index ->
                    val data = dataPlaylist.songs.get(index)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = data.image),
                                contentDescription = "song",
                                modifier = Modifier
                                    .weight(0.15f)
                                    .aspectRatio(1f)
                            )
                            Column(
                                modifier = Modifier
                                    .weight(0.7f)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    data.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(data.artistId.name, style = MaterialTheme.typography.labelSmall)
                            }
                            IconButton(onClick = {
                                    mainViewModel.convertPlaylistResponseToPlaylist(dataPlaylist, true, index)
                            }, modifier = Modifier.weight(0.15f)) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "play song"
                                )
                            }
                        }
                        Divider(thickness = 2.dp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlayPrev() {
    Scaffold(topBar = { TopAppBar(title = { }, navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
        }
    }) }){
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)
            .padding(24.dp)){
            item {
                Row(modifier= Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Image(painter = painterResource(id = R.drawable.music), contentDescription = "artist", modifier= Modifier
                        .fillMaxWidth(0.25f)
                        .aspectRatio(1f))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier= Modifier.fillMaxWidth()){
                        Text(text = "Rose Blackpink", style= MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.PlayCircleOutline, contentDescription = "")
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("Http://ndnndnd.com/hdgdg/hdhdhdh", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(20.dp))
                Text("All Songs", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(3){
                Column(modifier= Modifier.fillMaxWidth()){
                    Row(modifier= Modifier.fillMaxWidth()){
                        Image(painter = painterResource(id = R.drawable.music), contentDescription = "artist", modifier= Modifier
                            .weight(0.15f)
                            .aspectRatio(1f))
                        Column(modifier= Modifier
                            .weight(0.7f)
                            .padding(8.dp)) {
                            Text("Fallen into the Sky", style= MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(modifier= Modifier.height(4.dp))
                            Text("Rose Blackpink", style = MaterialTheme.typography.labelSmall)
                        }
                        IconButton(onClick = { /*TODO*/ }, modifier= Modifier.weight(0.15f)) {
                            Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "play song")
                        }
                    }
                    Divider(thickness = 2.dp)
                }
                Spacer(modifier= Modifier.height(4.dp))
            }
        }
    }
}