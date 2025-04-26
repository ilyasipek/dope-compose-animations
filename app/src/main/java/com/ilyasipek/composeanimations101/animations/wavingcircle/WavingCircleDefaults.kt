package com.ilyasipek.composeanimations101.animations.wavingcircle

import androidx.compose.runtime.Composable

object WavingCircleDefaults {

    private var _cachedProperties: NoiseProperties? = null
    private val cachedProperties: NoiseProperties
        @Composable
        get() {
            return _cachedProperties
                ?: NoiseProperties(
                    speedX = 1f,
                    scale = 4f,
                    amplitude = 0.07f
                ).also {
                    _cachedProperties = it
                }
        }

    @Composable
    fun noiseProperties(
        speedX: Float = Float.NaN,
        scale: Float = Float.NaN,
        amplitude: Float = Float.NaN,
    ): NoiseProperties =
        cachedProperties.copy(
            speedX = if (speedX.isNaN()) cachedProperties.speedX else speedX,
            scale = if (scale.isNaN()) cachedProperties.scale else scale,
            amplitude = if (amplitude.isNaN()) cachedProperties.amplitude else amplitude,
        )
}