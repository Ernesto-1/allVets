package com.example.allvets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.allvets.ui.home.AVHome
import com.example.allvets.ui.login.AVLogin
import com.example.allvets.ui.navigation.Menu
import com.example.allvets.ui.navigation.Route
import com.example.allvets.ui.theme.AllVetsTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllVetsTheme {
                val navigationController = rememberNavController()
                val user = Firebase.auth.currentUser
                var selectMenu by rememberSaveable { mutableStateOf("Inicio") }
                val items = listOf(
                    Menu(Route.AVHome, Icons.Filled.Home, "Inicio"),

                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Scaffold(bottomBar = {
                        val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination
                        if (currentDestination?.route in listOf(
                                Route.AVHome
                            )
                        ) {
                            if (currentDestination?.route == Route.AVHome) {
                                selectMenu = "Inicio"
                            }
                            BottomNavigation {
                                items.forEach { item ->
                                    BottomNavigationItem(modifier = Modifier.background(Color.White),
                                        icon = {
                                            Icon(
                                                item.icon,
                                                contentDescription = null,
                                                tint = if (selectMenu == item.label) Color(
                                                    0xff84B1B8
                                                ) else Color(0xffDEDEDE)
                                            )
                                        },
                                        label = {
                                            Text(
                                                item.label,
                                                color = if (selectMenu == item.label) Color(
                                                    0xff84B1B8
                                                ) else Color(
                                                    0xffDEDEDE
                                                ),
                                                fontSize = 10.sp
                                            )
                                        },
                                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                                        onClick = {
                                            if (user != null || item.route != Route.AVLogin) {
                                                navigationController.navigate(item.route) {
                                                    popUpTo(navigationController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                            selectMenu = item.label
                                        })
                                }
                            }

                        }
                    }){
                        NavHost(navController = navigationController,startDestination = Route.AVLogin){
                            composable(Route.AVLogin) { AVLogin(navController = navigationController) }
                            composable(Route.AVHome) { AVHome(navController = navigationController) }


                        }
                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AllVetsTheme {
        Greeting("Android")
    }
}