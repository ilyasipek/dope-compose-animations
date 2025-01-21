package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun RoundedRectDeterminateIndicator(
    progress: () -> Float,
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    trackColor: Color = MaterialTheme.colorScheme.surface,
    strokeCap: StrokeCap = StrokeCap.Round,
    strokeWidth: Dp = 8.dp
) {
    val progressValue by animateFloatAsState(
        progress(),
        label = "AnimatedProgressValue",
    )

    val path = remember { Path() }

    val pathWithProgress = remember { Path() }

    val pathMeasure = remember { PathMeasure() }

    Canvas(modifier) {
        if (path.isEmpty) {
            path.addRoundRect(
                roundRect = RoundRect(
                    rect = Rect(
                        offset = Offset.Zero,
                        size = size,
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx())
                ),
                direction = Path.Direction.Clockwise
            )
        }
        pathWithProgress.reset()

        pathMeasure.setPath(path, forceClosed = false)

        // get path x times and draw all of them
        pathMeasure.getSegment(
            startDistance = 0f,
            stopDistance = pathMeasure.length * progressValue,
            destination = pathWithProgress,
        )

        drawPath(
            path = path,
            color = trackColor,
            style = Stroke(width = strokeWidth.toPx()),
        )

        rotate(
            90f
        ) {
            drawPath(
                path = pathWithProgress,
                color = color,
                style = Stroke(width = strokeWidth.toPx(), cap = strokeCap),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoundedRectDeterminateIndicatorPreview() {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val state = rememberPagerState(
            initialPage = 0,
            pageCount = { 5 }
        )
        HorizontalPager(
            state
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text("Page ${state.currentPage}", fontSize = 32.sp, color = Color.White)
            }
        }
        Box(
            modifier = Modifier
                .padding(bottom = 56.dp)
                .size(72.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    coroutineScope.launch {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                },
            contentAlignment = Alignment.Center,
        ) {
            RoundedRectDeterminateIndicator(
                progress = { ((state.currentPage + 1).toFloat() / state.pageCount) },
                modifier = Modifier.fillMaxSize(),
            )
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = Color.White
            )
        }
    }
}