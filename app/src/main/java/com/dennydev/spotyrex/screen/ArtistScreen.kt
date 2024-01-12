package com.dennydev.spotyrex.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.PlayCircleOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.components.items
import com.dennydev.spotyrex.model.Constant
import com.dennydev.spotyrex.utils.formatDuration
import com.dennydev.spotyrex.viewmodel.ArtistViewModel
import com.dennydev.spotyrex.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistScreen(
    navController: NavHostController,
    artistViewModel: ArtistViewModel = hiltViewModel(),
    mainViewModel: MainViewModel,
    artistId: String
) {
    val artistResponse by artistViewModel.artistResponse

    LaunchedEffect(key1 = artistId) {
        artistViewModel.getDetaiArtist(artistId)
    }

    Scaffold(topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
            }
        })
    }) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        ) {
            artistResponse.data?.data?.let {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(model = "${Constant.BASE_URL}${it.image}"),
                            contentDescription = "artist",
                            modifier = Modifier
                                .fillMaxWidth(0.25f)
                                .aspectRatio(1f)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            IconButton(onClick = {
                                mainViewModel.convertArtistResponseToPlaylist(it, true, 0)
                            }) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircleOutline,
                                    contentDescription = ""
                                )
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        it.desc,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        "All Songs",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                items(it.songs.size) {index ->
                    val song = it.songs.get(index)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Image(
                                painter = rememberAsyncImagePainter(model = song.image),
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
                                    song.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(formatDuration((song.duration*1000).toDouble()), style = MaterialTheme.typography.labelSmall)
                            }
                            IconButton(onClick = {
                                mainViewModel.convertArtistResponseToPlaylist(it, true, index)
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
fun PrevArtist() {
    Scaffold(topBar = {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.ArrowBackIos, contentDescription = "back")
            }
        })
    }) {
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
                        painter = painterResource(id = R.drawable.music),
                        contentDescription = "artist",
                        modifier = Modifier
                            .fillMaxWidth(0.25f)
                            .aspectRatio(1f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Rose Blackpink",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.PlayCircleOutline,
                                contentDescription = ""
                            )
                        }
                    }

                }
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "Http://ndnndnd.com/hdgdg/hdhdhdh",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    "All Songs",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
            }
            items(3) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Image(
                            painter = painterResource(id = R.drawable.music),
                            contentDescription = "artist",
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
                                "Fallen into the Sky",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Rose Blackpink", style = MaterialTheme.typography.labelSmall)
                        }
                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(0.15f)) {
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