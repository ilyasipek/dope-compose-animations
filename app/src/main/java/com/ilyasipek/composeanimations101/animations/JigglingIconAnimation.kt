package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyasipek.composeanimations101.ui.theme.ComposeAnimations101Theme
import com.ilyasipek.composeanimations101.utils.DataProviderUtil

// you can add more transform-origins to make the animation even more random
private val transformOrigins = listOf(
    TransformOrigin(0.2f, 0.05f),
    TransformOrigin(0.5f, 0.10f),
    TransformOrigin(0.5f, 0.5f),
    TransformOrigin(0.3f, 0.7f),
    TransformOrigin(0.9f, 0.07f),
)

private const val BASE_ROTATION_DEGREE = .3f

/**
 * Basically, we're rotating the item between
 * -[BASE_ROTATION_DEGREE]- random-extra to [BASE_ROTATION_DEGREE]+ random-extra sometimes more toward the right sometimes toward the left
 * based on a random boolean (isMoreJigglingOnRight)
 *
 * Also, we're changing the transformOrigins to make it look like it's anchored from the top-corners or top-center
 * in a random way.
 *
 * In short, pretty random dope animation :)
 * */
@Composable
fun JigglingIconAnimation(
    icon: ImageVector,
    inSelectionMode: Boolean,
    modifier: Modifier = Modifier,
) {
    val isMoreJigglingOnRight = remember {
        (1..2).random() == 1
    }
    val randomExtra: Float = remember {
        (200..400).random().toFloat() / 400f
    }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedDegree by infiniteTransition.animateFloat(
        initialValue = -BASE_ROTATION_DEGREE - if (isMoreJigglingOnRight) randomExtra / 2f else randomExtra,
        targetValue = BASE_ROTATION_DEGREE + if (isMoreJigglingOnRight) randomExtra else randomExtra / 2f,
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = 120,
                easing = EaseInOutQuad
            ),
            repeatMode = RepeatMode.Reverse,
        ), label = "AnimatedDegree"
    )

    val transformOrigin = remember {
        transformOrigins.random()
    }
    val colors = remember { DataProviderUtil.colors.random() }



    Column(
        Modifier.then(
            if (inSelectionMode) Modifier.graphicsLayer {
                this.rotationZ = animatedDegree
                this.transformOrigin = transformOrigin
            } else Modifier
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = modifier
                .background(colors, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                inSelectionMode,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .offset(x = (-3).dp, (-3).dp),
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                Icon(
                    imageVector = Icons.Rounded.Remove,
                    contentDescription = "Jiggling Icon",
                    tint = Color.White,
                    modifier = Modifier
                        .background(Color.Gray, CircleShape)
                        .padding(2.dp)
                        .size(12.dp)
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = "Jiggling Icon",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            )
        }

        Text("Dope", fontSize = 12.sp)
    }

}

@Preview
@Composable
private fun JiggleIconPreview() {
    ComposeAnimations101Theme {
        JigglingIconAnimation(
            modifier = Modifier.aspectRatio(1f),
            inSelectionMode = true,
            icon = DataProviderUtil.roundedIcons.first()
        )
    }
}

@Preview
@Composable
fun JiggleIconGridSample() {
    ComposeAnimations101Theme {
        var isInSelectionMode by remember {
            mutableStateOf(false)
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            contentPadding = PaddingValues(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            repeat(21) {
                item {
                    JigglingIconAnimation(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (isInSelectionMode) {
                                            isInSelectionMode = false
                                        }
                                    },
                                    onLongPress = {
                                        isInSelectionMode = !isInSelectionMode
                                    }
                                )
                            },
                        inSelectionMode = isInSelectionMode,
                        icon = DataProviderUtil.roundedIcons[it]
                    )
                }
            }
        }
    }
}