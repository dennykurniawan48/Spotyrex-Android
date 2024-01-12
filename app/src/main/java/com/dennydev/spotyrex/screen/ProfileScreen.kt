package com.dennydev.spotyrex.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.components.BottomNav
import com.dennydev.spotyrex.components.googleSignOut
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.viewmodel.MainViewModel
import com.dennydev.spotyrex.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel = hiltViewModel(),
    mainViewModel: MainViewModel
) {
    val token by profileViewModel.token.collectAsState(initial = "")
    val profileResponse by profileViewModel.profileResponse
    val selectedBottom by mainViewModel.selecteBottomNav
    val playerState by mainViewModel.playerState.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(key1 = token){
        if(token.isEmpty()){
            navController.navigate(Screen.LoginScreen.route){
                popUpTo(Screen.LoginScreen.route){
                    inclusive = true
                }
            }
        }
        profileViewModel.getProfile(token)
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
        CenterAlignedTopAppBar(title = { Text("Profile", fontWeight = FontWeight.Bold) })
    }) {
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(48.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            profileResponse.data?.data?.let {
                Image(
                    painter = painterResource(id = R.drawable.earphone),
                    contentDescription = "profile picture",
                    modifier = Modifier.size(200.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(it.name, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(it.email, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    googleSignOut(context){
                        profileViewModel.logout()
                    }
                }) {
                    Text("Logout", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun OrevProfile() {
    Scaffold(bottomBar = {

    }, topBar = {
        CenterAlignedTopAppBar(title = { Text("Profile", fontWeight = FontWeight.Bold) })
    }) {
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(48.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.earphone), contentDescription = "profile picture", modifier = Modifier.size(200.dp))
            Spacer(modifier=Modifier.height(24.dp))
            Text("Roseanne Park", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier=Modifier.height(8.dp))
            Text("hello@gmail.com", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier=Modifier.height(24.dp))
            Button(onClick = { /*TODO*/ }) {
                Text("Logout", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}