package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.ui.theme.ComposeAnimations101Theme
import com.ilyasipek.composeanimations101.utils.clickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val toggleWidth = 52.dp
private val toggleHeight = 32.dp
private val indicatorSize = 24.dp
private val horizontalPadding = 4.dp
private const val DELAY_BETWEEN_HEAD_AND_TAIL_ANIMATION = 150L

// TODO: Convert this animation to Modifier Node API with measure node like Switch api imp
@Composable
fun WormToggleAnimation(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    colors: SwitchColors = SwitchDefaults.colors()
) {
    val layoutDirection = LocalLayoutDirection.current

    val headXPosAnimation = remember {
        Animatable(getHeadXPos(checked), Dp.VectorConverter)
    }
    val tailXPosAnimation = remember {
        Animatable(getTailXPos(checked), Dp.VectorConverter)
    }

    LaunchedEffect(checked) {
        val headTargetXPos = getHeadXPos(checked)
        val tailTargetXPos = getTailXPos(checked)

        if (checked) {
            launch { headXPosAnimation.animateTo(headTargetXPos) }
            delay(DELAY_BETWEEN_HEAD_AND_TAIL_ANIMATION)
            tailXPosAnimation.animateTo(tailTargetXPos)
        } else {
            launch { tailXPosAnimation.animateTo(tailTargetXPos) }
            delay(DELAY_BETWEEN_HEAD_AND_TAIL_ANIMATION)
            headXPosAnimation.animateTo(headTargetXPos)
        }
    }

    Box(
        modifier
            .requiredSize(toggleWidth, toggleHeight)
            .border(
                width = 1.dp,
                color = if (checked) colors.checkedBorderColor else colors.uncheckedTrackColor,
                shape = CircleShape
            )
            .background(
                if (checked) {
                    colors.checkedTrackColor
                } else {
                    colors.uncheckedTrackColor
                },
                CircleShape
            )
            .clickable(rippleEnabled = false) {
                onCheckedChange?.invoke(checked.not())
            },
    ) {
        Spacer(
            modifier = Modifier
                .padding(horizontalPadding)
                .requiredSize(indicatorSize)
                .drawBehind {
                    // Adjust coordinates for RTL:
                    val (adjustedTail, adjustedHead) = if (layoutDirection == LayoutDirection.Rtl) {
                        // In RTL, flip the coordinates.
                        val adjustedTail = size.width - headXPosAnimation.value.toPx()
                        val adjustedHead = size.width - tailXPosAnimation.value.toPx()
                        adjustedTail to adjustedHead
                    } else {
                        tailXPosAnimation.value.toPx() to headXPosAnimation.value.toPx()
                    }
                    drawWormIndicator(
                        tailXPos = adjustedTail,
                        headXPos = adjustedHead,
                        color = if (checked) colors.checkedThumbColor else colors.uncheckedThumbColor
                    )
                }
                .graphicsLayer {
                    scaleX = 0.5f
                    scaleY = 0.5f
                }
        )
    }
}

private fun DrawScope.drawWormIndicator(
    tailXPos: Float,
    headXPos: Float,
    color: Color,
) {
    val path = Path().apply {
        addRoundRect(
            roundRect = RoundRect(
                left = tailXPos,
                top = 0f,
                right = headXPos,
                bottom = size.height,
                cornerRadius = CornerRadius(360f, 360f)
            ),
        )
    }
    drawPath(path, color)
}

private fun getHeadXPos(checked: Boolean): Dp = if (checked) {
    toggleWidth - (horizontalPadding * 2)
} else {
    indicatorSize
}

private fun getTailXPos(checked: Boolean): Dp = if (checked) {
    toggleWidth - indicatorSize - (horizontalPadding * 2)
} else {
    0.dp
}

@Composable
fun CheckedWormToggleAnimationSample(
    modifier: Modifier = Modifier,
) {
    var checked by remember {
        mutableStateOf(true)
    }
    WormToggleAnimation(
        checked,
        onCheckedChange = {
            checked = it
        },
        modifier = modifier.padding(16.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun CheckedWormToggleAnimationPreview() {
    ComposeAnimations101Theme {
        WormToggleAnimation(
            true,
            onCheckedChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0x000000)
@Composable
private fun UnCheckedWormToggleAnimationPreview() {
    ComposeAnimations101Theme {
        WormToggleAnimation(
            false,
            onCheckedChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
