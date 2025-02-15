package com.ilyasipek.composeanimations101

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object ScrambleSampleScreen

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = MainScreen) {
        composable<MainScreen> {
            MainScreen(
                onNavigateTo = {
                    navController.navigate(it)
                }
            )
        }
        composable<ScrambleSampleScreen> {
            Box(modifier = Modifier) {
                Text("scramble")
            }
        }
    }
}