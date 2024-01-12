package com.dennydev.spotyrex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.dennydev.spotyrex.navigation.Screen
import com.dennydev.spotyrex.navigation.SetupNavigation
import com.dennydev.spotyrex.repository.AuthStoreRepository
import com.dennydev.spotyrex.ui.theme.SpotyrexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var showSplashScren = true
        val authStoreRepository = AuthStoreRepository(this)
        var startDestination = Screen.LoginScreen.route
        lifecycleScope.launch {
            var isLoggedIn = false
            launch(Dispatchers.Default) {
                authStoreRepository.flowToken.collect {
                    isLoggedIn = it.isNotEmpty()
                }
            }
            if(isLoggedIn){
                startDestination = Screen.HomeScreen.route
            }
            delay(1500L)
            showSplashScren = false
        }

        installSplashScreen().setKeepOnScreenCondition{
            showSplashScren
        }

        setContent {
            val navController = rememberNavController()

            SpotyrexTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(navController = navController, startDestination = startDestination)
                }
            }
        }
    }
}