package com.dennydev.spotyrex.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.dennydev.spotyrex.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.dennydev.spotyrex.components.GoogleSignInHelper
import com.dennydev.spotyrex.model.ApiResponse
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val googleSelected by loginViewModel.isGoogleSignIn
    val resultLogin by loginViewModel.loginResponse
    val context = LocalContext.current
    val token by loginViewModel.token.collectAsState(initial = "")

    LaunchedEffect(key1 = token){
        if(token.isNotEmpty()) {
            navController.navigate(Screen.HomeScreen.route) {
                popUpTo(Screen.HomeScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold() {
        GoogleSignInHelper(context = context, onGoogleSignInSeleced = googleSelected, onDismiss = {loginViewModel.onChangeIsGoogleLogin(false)}, onResultReceived = {
                token -> loginViewModel.loginWithGoogle(token)
        })
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            Image(painter = painterResource(id = R.drawable.music), contentDescription = "logo")
            OutlinedButton(onClick = {
                if(resultLogin !is ApiResponse.Loading) {
                    loginViewModel.onChangeIsGoogleLogin(true)
                }
            }) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Icon(painter = painterResource(id = R.drawable.google), contentDescription = "login with google")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = if(resultLogin !is ApiResponse.Loading) "Login with google" else "Loading", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginPrev(

) {
    Scaffold() {
        Column(modifier= Modifier
            .fillMaxSize()
            .padding(it)
            .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {
            Image(painter = painterResource(id = R.drawable.music), contentDescription = "logo")
            OutlinedButton(onClick = { /*TODO*/ }) {
                Row(verticalAlignment = Alignment.CenterVertically){
                  Icon(painter = painterResource(id = R.drawable.google), contentDescription = "login with google")
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Login with google", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}