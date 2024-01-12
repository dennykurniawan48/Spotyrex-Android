package com.dennydev.spotyrex.screen

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOn
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.ShuffleOn
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.utils.formatDuration
import com.dennydev.spotyrex.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(navController:NavHostController, mainViewModel: MainViewModel) {
    val playerState by mainViewModel.playerState.collectAsState()
    var currentPosition by remember{ mutableStateOf(0f) }

    LaunchedEffect(key1 = playerState){
        currentPosition = playerState.currentPosition.toFloat()
        if(playerState.currentAudio == null){
            navController.popBackStack()
        }
        while(playerState.isPlaying){
            delay(800L)
            mainViewModel.getCurrentPosition()
        }
    }

    Scaffold(topBar = { TopAppBar(title = { }, navigationIcon = {
        IconButton(onClick = {
            navController.popBackStack()
        }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "back")
        }
    }) }){
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(horizontal=36.dp, vertical=12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly){
            Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Image(painter = rememberAsyncImagePainter(model = playerState.currentAudio?.thumbnail), contentDescription = "", modifier= Modifier
                    .width(250.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .aspectRatio(1f))
                Spacer(modifier=Modifier.height(36.dp))
                Text(playerState.currentAudio?.title ?: "", style=MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                TextButton(onClick={
                    playerState.currentAudio?.let {
                        navController.navigate(Screen.ArtistScreen.route.replace("{artistId}", it.artistId))
                    }
                }){
                    Text(playerState.currentAudio?.artist ?: "", style=MaterialTheme.typography.bodyLarge)
                }
            }
            Column(){
                Slider(
                    value = currentPosition,
                    onValueChange = { },
                    steps = 1000,
                    valueRange = 0f..(playerState.currentAudio?.duration?.toFloat() ?: 0f)
                )
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text(formatDuration(currentPosition.toDouble()))
                    Text(formatDuration(playerState.currentAudio?.duration?.toDouble() ?: 0.0))
                }
                Spacer(modifier=Modifier.height(16.dp))
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = {
                        mainViewModel.setShuffleEnabled(!playerState.shuffle)
                    }) {
                        Icon(imageVector = if (playerState.shuffle) Icons.Default.ShuffleOn else Icons.Default.Shuffle, contentDescription = "Shuffle")
                    }
                    IconButton(onClick = {
                        mainViewModel.skipPrev()
                    }) {
                        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "Previous")
                    }
                    IconButton(onClick = {
                           if(playerState.isPlaying){
                               mainViewModel.pause()
                           }else{
                               mainViewModel.resume()
                           }
                    }, modifier= Modifier
                        .width(80.dp)
                        .aspectRatio(1f)) {
                            Icon(
                                imageVector = if(playerState.isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle,
                                contentDescription = if(playerState.isPlaying) "Pause" else "Play",
                                modifier = Modifier
                                    .width(60.dp)
                                    .aspectRatio(1f)
                            )
                    }
                    IconButton(onClick = { mainViewModel.skipNext() }) {
                        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "Next")
                    }
                    IconButton(onClick = {
                        mainViewModel.setRepeatEnabled(!playerState.repeat)
                    }) {
                        Icon(imageVector = if (playerState.repeat) Icons.Default.RepeatOn else Icons.Default.Repeat, contentDescription = "Repeat")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PlayPrevScreen() {
    Scaffold(topBar = { TopAppBar(title = { }, navigationIcon = {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "back")
        }
    }) }){
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(36.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly){
            Column(modifier=Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Image(painter = painterResource(id = R.drawable.music), contentDescription = "", modifier= Modifier
                    .width(250.dp)
                    .clip(
                        RoundedCornerShape(20.dp)
                    )
                    .aspectRatio(1f))
                Spacer(modifier=Modifier.height(36.dp))
                Text("Rose Blackpink", style=MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier=Modifier.height(16.dp))
                Text("Rose Blackpink", style=MaterialTheme.typography.bodyLarge)
            }
            Column(){
                Slider(
                    value = 10f,
                    onValueChange = { },
                    steps = 1,
                    valueRange = 0f..100f
                )
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
                    Text("00:00")
                    Text("03:00")
                }
                Spacer(modifier=Modifier.height(16.dp))
                Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Shuffle, contentDescription = "Shuffle")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.SkipPrevious, contentDescription = "Previous")
                    }
                    IconButton(onClick = { /*TODO*/ }, modifier= Modifier
                        .width(80.dp)
                        .aspectRatio(1f)) {
                        Icon(imageVector = Icons.Default.PlayCircle, contentDescription = "Play", modifier= Modifier
                            .width(60.dp)
                            .aspectRatio(1f))
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.SkipNext, contentDescription = "Next")
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Repeat, contentDescription = "Repeat")
                    }
                }
            }
        }
    }
}