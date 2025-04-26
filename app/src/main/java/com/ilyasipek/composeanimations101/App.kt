package com.ilyasipek.composeanimations101

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilyasipek.composeanimations101.samples.JigglingIconsSampleScreen
import com.ilyasipek.composeanimations101.samples.ScrambleTextAnimationSampleScreen
import com.ilyasipek.composeanimations101.samples.ShakeModifierSampleScreen
import com.ilyasipek.composeanimations101.samples.WavingCircleSampleScreen

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
        composable<ShakeModifierSampleScreen> {
            ShakeModifierSampleScreen(onBackClick = { navController.popBackStack() })
        }
        composable<ScrambleTextAnimationSampleScreen> {
            ScrambleTextAnimationSampleScreen(onBackClick = { navController.popBackStack() })
        }
        composable<JigglingIconsSampleScreen> {
            JigglingIconsSampleScreen(onBackClick = { navController.popBackStack() })
        }
        composable<WavingCircleSampleScreen> {
            WavingCircleSampleScreen(onBackClick = { navController.popBackStack() })
        }
    }
}