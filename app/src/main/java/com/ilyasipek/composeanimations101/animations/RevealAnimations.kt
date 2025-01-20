package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.layer.CompositingStrategy.Companion.Offscreen
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.R


// NOTE....
//
// All of this examples can be converted to better components
// or just a reusable modifiers. also changing the direction of the reveal and stuff.
// But that's this is just the basic idea that you can play and make it according to your needs.

@Composable
fun ExpandShrinkRevealRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .background(Color(0xffA94A4A))
                .fillMaxSize()
        )
        AnimatedVisibility(
            isRevealed,
            enter = expandIn(),
            exit = shrinkOut()
        ) {
            Image(
                painter = painterResource(
                    R.drawable.animal1
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun HorizontalCurtainRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    val curtainVisibilityFraction by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 1f
    )
    Image(
        painter = painterResource(
            R.drawable.animal2
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                this@drawWithContent.drawContent()
                val contentHalfSize = size.width / 2
                drawRect(
                    Color(0xffF4D793),
                    size = Size(
                        height = size.height,
                        width = contentHalfSize * curtainVisibilityFraction
                    )
                )
                drawRect(
                    Color(0xffF4D793),
                    topLeft = Offset(
                        x = contentHalfSize + contentHalfSize * (1 - curtainVisibilityFraction),
                        y = 0f
                    )
                )
            }
    )
}

@Composable
fun VerticalCurtainRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    val curtainVisibilityFraction by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 1f
    )
    Image(
        painter = painterResource(
            R.drawable.animal3
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .drawWithContent {
                this@drawWithContent.drawContent()
                val contentHalfSize = size.height / 2

                // upper curtain
                drawRect(
                    Color(0xffFFF6DA),
                    size = Size(
                        height = contentHalfSize * curtainVisibilityFraction,
                        width = size.width
                    )
                )

                // bottom curtain
                drawRect(
                    Color(0xffFFF6DA),
                    topLeft = Offset(
                        x = 0f,
                        y = contentHalfSize + contentHalfSize * (1 - curtainVisibilityFraction)
                    )
                )
            }
    )
}

@Composable
fun ScaleRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .background(Color(0xff889E73))
                .fillMaxSize()
        )

        AnimatedVisibility(
            isRevealed,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
            Image(
                painter = painterResource(
                    R.drawable.animal4
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun VerticalSaturatedCurtainRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    val curtainVisibilityFraction by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 1f,
        label = ""
    )
    Image(
        painter = painterResource(
            R.drawable.animal5
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                val graphicsLayer = obtainGraphicsLayer().apply {
                    record {
                        // you can also just record the content drawing itself without the blend mode
                        // drawContent() and remove blend Mode
                        // this will create draw the original content with 0 saturation
                        // and draws it above the original content.
                        // [LeftSideSaturatedCurtainRevealAnimation] is implemented this way
                        drawRect(SolidColor(Color.Black))
                    }
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(0f)
                    })
                    compositingStrategy = Offscreen
                    // replaces the saturation of the destination (image itself)
                    // with the source (the solid color) saturation
                    blendMode = BlendMode.Saturation
                }
                val contentHalfSize = size.height / 2

                val path = Path().apply {
                    // upper curtain
                    addRect(
                        rect = Rect(
                            offset = Offset.Zero,
                            size = Size(
                                height = contentHalfSize * curtainVisibilityFraction,
                                width = size.width
                            )
                        )
                    )

                    // bottom curtain
                    addRect(
                        rect = Rect(
                            offset = Offset(
                                x = 0f,
                                y = contentHalfSize + contentHalfSize * (1 - curtainVisibilityFraction)
                            ),
                            size = Size(
                                height = contentHalfSize * curtainVisibilityFraction,
                                width = size.width
                            )
                        )
                    )
                }
                onDrawWithContent {
                    drawContent()

                    // draw saturated content on top of the original content and clip it using the path.
                    clipPath(path) {
                        drawLayer(graphicsLayer)
                    }
                }
            }
    )
}

@Composable
fun LeftSideSaturatedCurtainRevealAnimation(
    isRevealed: Boolean,
    modifier: Modifier = Modifier
) {
    val curtainVisibilityFraction by animateFloatAsState(
        targetValue = if (isRevealed) 0f else 1f
    )
    Image(
        painter = painterResource(
            R.drawable.animal6
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier
            .fillMaxSize()
            .drawWithCache {
                val graphicsLayer = obtainGraphicsLayer().apply {
                    record {
                        this.drawContent()
                    }
                    colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                        setToSaturation(0f)
                    })
                    compositingStrategy = Offscreen
                }
                onDrawWithContent {
                    drawContent()

                    clipRect(
                        right = size.width * curtainVisibilityFraction
                    ) {
                        drawLayer(graphicsLayer)
                    }
                }
            }
    )
}


@Preview
@Composable
private fun RevealBoxAnimationPreview() {
    var isRevealed by remember {
        mutableStateOf(false)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(top = 56.dp)
    ) {
        item {
            ExpandShrinkRevealRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }
        item {
            ScaleRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }
        item {
            HorizontalCurtainRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }

        item {
            VerticalCurtainRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }
        item {
            VerticalSaturatedCurtainRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }
        item {
            LeftSideSaturatedCurtainRevealAnimation(
                isRevealed,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f)
                    .clickable {
                        isRevealed = !isRevealed
                    }
            )
        }
    }
}