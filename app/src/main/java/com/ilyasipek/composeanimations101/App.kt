package com.ilyasipek.composeanimations101

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilyasipek.composeanimations101.samples.JigglingIconsSampleScreen
import com.ilyasipek.composeanimations101.samples.ScrambleTextAnimationSampleScreen
import com.ilyasipek.composeanimations101.samples.ShakeModifierSampleScreen
import com.ilyasipek.composeanimations101.samples.WavingCircleSampleScreen
import com.ilyasipek.composeanimations101.samples.m3expressive.M3EPullToRefreshSampleScreen
import com.ilyasipek.composeanimations101.samples.m3expressive.M3ExpressiveButtonGroupSampleScreen
import com.ilyasipek.composeanimations101.samples.m3expressive.M3ExpressivePlaygroundSampleScreen

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
        composable<M3ExpressivePlaygroundSampleScreen> {
            M3ExpressivePlaygroundSampleScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateTo = {
                    navController.navigate(it)
                }
            )
        }
        composable<M3EPullToRefreshSampleScreen> {
            M3EPullToRefreshSampleScreen(onBackClick = { navController.popBackStack() })
        }
        composable<M3ExpressiveButtonGroupSampleScreen> {
            M3ExpressiveButtonGroupSampleScreen(onBackClick = { navController.popBackStack() })
        }
    }
}