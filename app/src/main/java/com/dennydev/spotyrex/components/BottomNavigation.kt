package com.dennydev.spotyrex.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.dennydev.spotyrex.R
import com.dennydev.spotyrex.database.Songs
import com.dennydev.spotyrex.navigation.Screen

val items = listOf(
    Screen.HomeScreen,
    Screen.ProfileScreen,
//    Screen.SettingScreen
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNav(navController: NavHostController, selectedBottom: String, onChangeSelectedBottomIndex: (route: String) -> Unit, song: Songs?) {
    Column(modifier= Modifier.fillMaxWidth()) {
        song?.let {
            Card(modifier=Modifier.fillMaxWidth(), onClick = {
                navController.navigate(Screen.PlayerScreen.route)
            }) {
                Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
                    Image(
                        painter = rememberAsyncImagePainter(model = it.thumbnail),
                        contentDescription = "song art",
                        modifier = Modifier.size(40.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(
                        modifier = Modifier
                            .height(40.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.Center
                    ) {
                        Text(it.title, style = MaterialTheme.typography.titleMedium)
                        Text(it.artist, style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedBottom == item.route,
                    onClick = {
                        onChangeSelectedBottomIndex(item.route)
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    label = {
                        Text(text = item.title)
                    },
                    alwaysShowLabel = false,
                    icon = {
                        Icon(
                            imageVector = if (item.route == selectedBottom) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    }
                )
            }
        }
    }
}