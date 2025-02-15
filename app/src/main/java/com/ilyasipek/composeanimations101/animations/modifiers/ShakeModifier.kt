package com.ilyasipek.composeanimations101.animations.modifiers

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun rememberShakeController(shakeConfig: ShakeConfig): ShakeController {
    return remember(shakeConfig) { ShakeController(shakeConfig) }
}

class ShakeController(
    val shakeConfig: ShakeConfig
) {
    private val shakeAnimatable = Animatable(0f)

    val shakeFraction: Float
        get() = shakeAnimatable.value

    suspend fun shake(animationSpec: AnimationSpec<Float> = spring(stiffness = 100_000f)) {
        // animates to right then left then after the iteration, back to origin.
        repeat(shakeConfig.iterations) {
            when {
                it % 2 == 0 -> shakeAnimatable.animateTo(1f, animationSpec)
                else -> shakeAnimatable.animateTo(-1f, animationSpec)
            }
        }
        shakeAnimatable.animateTo(0f)
    }
}

/**
 * Check the ShakeModifierSampleScreen for a practical example
 * */
fun Modifier.shake(shakeController: ShakeController): Modifier {
    val shakeConfig = shakeController.shakeConfig
    val shakeFraction = shakeController.shakeFraction
    return this.graphicsLayer {
        translationX = shakeConfig.transitionX * shakeFraction
        translationY = shakeConfig.transitionY * shakeFraction
    }
}

data class ShakeConfig(
    val iterations: Int,
    val transitionX: Float,
    val transitionY: Float,
)