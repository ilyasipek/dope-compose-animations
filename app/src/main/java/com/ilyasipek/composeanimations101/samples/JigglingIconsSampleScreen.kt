package com.ilyasipek.composeanimations101.samples

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.animations.BASE_ROTATION_DEGREE
import com.ilyasipek.composeanimations101.animations.JigglingIconAnimation
import com.ilyasipek.composeanimations101.ui.theme.ComposeAnimations101Theme
import com.ilyasipek.composeanimations101.utils.DataProviderUtil
import com.ilyasipek.composeanimations101.utils.horizontalPadding
import kotlinx.serialization.Serializable

@Serializable
object JigglingIconsSampleScreen

@Composable
fun JigglingIconsSampleScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = { SampleToolbar("Jiggling Icons", onBackClick = onBackClick) }
    ) {
        JiggleIconGridContent(modifier = Modifier.padding(it))
    }
}

@Composable
fun JiggleIconGridContent(
    modifier: Modifier = Modifier,
) {
    ComposeAnimations101Theme {
        var isInSelectionMode by remember {
            mutableStateOf(false)
        }
        var baseRotationDegree by remember {
            mutableFloatStateOf(BASE_ROTATION_DEGREE)
        }


        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = modifier
            ) {
                repeat(21) {
                    item {
                        JigglingIconAnimation(
                            baseRotationDegree = baseRotationDegree,
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

            Spacer(modifier = Modifier.weight(1f))

            Text(
                "Base rotation: (${baseRotationDegree})",
                modifier = Modifier.horizontalPadding(24.dp),
                color = Color.Black,
            )

            Slider(
                value = baseRotationDegree,
                onValueChange = {
                    baseRotationDegree = it
                },
                valueRange = 0.1f..5f,
                modifier = Modifier
                    .horizontalPadding(24.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}