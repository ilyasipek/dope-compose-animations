package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.verticalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.VolumeUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.SliderState
import androidx.compose.material3.VerticalSlider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.abs

/*
* A good article i learned a lot from https://www.sinasamaki.com/implementing-overslide-slider-interaction-in-jetpack-compose/
* */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StretchyVerticalVolumeSlider(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    // slider state.
    val sliderState = remember {
        SliderState(50f, valueRange = 0f..100f)
    }
    val sliderNormalizedValue = remember {
        derivedStateOf {
            sliderState.value / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
        }
    }

    // track stretch transformers
    var overslideScaleX by remember {
        mutableFloatStateOf(1f)
    }
    var overslideScaleY by remember {
        mutableFloatStateOf(1f)
    }
    var overslideTranslateY by remember {
        mutableFloatStateOf(0f)
    }
    var transformOrigin by remember {
        mutableStateOf(TransformOrigin.Center)
    }

    VerticalSlider(
        state = sliderState,
        modifier = modifier,
        reverseDirection = true,
        thumb = {},
        track = {
            Box(
                modifier = Modifier
                    .trackOverslide(sliderNormalizedValue.value) { overSlideValue ->
                        println("overslide: $overSlideValue")
                        transformOrigin = TransformOrigin(
                            pivotFractionX = .5f,
                            pivotFractionY = if (sliderNormalizedValue.value < .5f) 0f else 1f,
                        )

                        val absolutesOverSlideValue = abs(overSlideValue)
                        overslideScaleY = 1f + absolutesOverSlideValue * .1f
                        overslideScaleX = 1f - absolutesOverSlideValue * .1f

                        overslideTranslateY = overSlideValue * with(density) { 4.dp.toPx() }
                    }
                    .graphicsLayer {
                        this.scaleX = overslideScaleX
                        this.scaleY = overslideScaleY
                        this.transformOrigin = transformOrigin
                        this.translationY = overslideTranslateY
                    }
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color(0xff262A33)),
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .background(Brush.verticalGradient(colors = listOf(Color.Gray, Color.LightGray)))
                        .fillMaxWidth()
                        .fillMaxHeight(sliderNormalizedValue.value)
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.VolumeUp,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                )
            }
        },
    )
}

/***
 * @param value: the normalized value of the slider. between 0f..1f
 * @param onNewOverslideAmount: callback to update the overslide amount.
 * Overslide amount is the percentage of the composable height (between -1f and 1f)
 * */
@Composable
fun Modifier.trackOverslide(
    value: Float,
    onNewOverslideAmount: (Float) -> Unit,
): Modifier {
    var height by remember { mutableFloatStateOf(0f) }
    val valueState = rememberUpdatedState(value)
    val overslideAmountAnimatable = remember {
        Animatable(0f)
    }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        snapshotFlow { overslideAmountAnimatable.value }.collect {
            onNewOverslideAmount((it / height).coerceIn(-1f, 1f))
        }
    }

    return this
        .onSizeChanged { height = it.height.toFloat() }
        .pointerInput(Unit) {
            awaitEachGesture {
                val downInputId = awaitFirstDown().id

                var overDragAmount = 0f
                verticalDrag(downInputId) { change ->
                    val deltaY = change.positionChange().y
                    overDragAmount = when (valueState.value) {
                        0f, 1f -> overDragAmount + deltaY
                        else -> 0f
                    }

                    coroutineScope.launch {
                        overslideAmountAnimatable.animateTo(overDragAmount)
                    }
                }

                // animate overslide back to zero when the drag gesture ends.
                coroutineScope.launch {
                    overslideAmountAnimatable.animateTo(
                        0f,
                        animationSpec = spring(
                            stiffness = Spring.StiffnessLow,
                            dampingRatio = Spring.DampingRatioLowBouncy
                        )
                    )
                }
            }
        }
}

@Preview
@Composable
private fun StretchyVerticalVolumeSliderSample() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff13141B))
    ) {
        StretchyVerticalVolumeSlider(
            modifier = Modifier.height(260.dp)
        )
    }
}