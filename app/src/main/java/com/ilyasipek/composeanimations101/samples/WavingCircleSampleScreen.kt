package com.ilyasipek.composeanimations101.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.animations.wavingcircle.WavingCircle
import com.ilyasipek.composeanimations101.animations.wavingcircle.WavingCircleDefaults
import com.ilyasipek.composeanimations101.utils.clickable
import kotlinx.serialization.Serializable

@Serializable
object WavingCircleSampleScreen

@Composable
fun WavingCircleSampleScreen(modifier: Modifier = Modifier, onBackClick: () -> Unit) {

    Scaffold(
        topBar = {
            SampleToolbar(
                title = "Waving Circle",
                onBackClick = onBackClick
            )
        }) {
        Column(
            modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            var speedX by remember { mutableFloatStateOf(1f) }
            var scale by remember { mutableFloatStateOf(4f) }
            var amplitude by remember { mutableFloatStateOf(0.07f) }

            WavingCircle(
                modifier = Modifier
                    .size(128.dp)
                    .clickable {},
                color = MaterialTheme.colorScheme.primary,
                noiseProperties = WavingCircleDefaults.noiseProperties(
                    speedX = speedX,
                    scale = scale,
                    amplitude = amplitude,
                )
            )

            NoiseSlider(
                modifier = Modifier.padding(16.dp),
                textPrefix = "SpeedX",
                value = speedX,
                valueRange = 0f..5f,
                onValueChange = {
                    speedX = it
                }
            )
            NoiseSlider(
                modifier = Modifier.padding(16.dp),
                textPrefix = "Scale",
                value = scale,
                valueRange = 1f..10f,
                onValueChange = {
                    scale = it
                }
            )
            NoiseSlider(
                modifier = Modifier.padding(16.dp),
                textPrefix = "Amplitude",
                value = amplitude,
                valueRange = 0.01f..0.08f,
                onValueChange = {
                    amplitude = it
                }
            )
        }
    }
}

@Composable
private fun NoiseSlider(
    textPrefix: String,
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Text(
            text = "$textPrefix: ${"%.2f".format(value)}"
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Slider(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            valueRange = valueRange,
            onValueChange = onValueChange
        )
    }
}