package com.ilyasipek.composeanimations101.samples.m3expressive

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.samples.SampleToolbar
import kotlinx.serialization.Serializable

@Serializable
object M3ExpressiveButtonGroupSampleScreen

@Composable
fun M3ExpressiveButtonGroupSampleScreen(
    onBackClick: () -> Unit,
) {
    Scaffold(
        topBar = { SampleToolbar("M3 Expressive", onBackClick = onBackClick) }
    ) {
        Content(
            modifier = Modifier.padding(it),
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
    val numpadItems = listOf(
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "",
        "0",
        ""
    )
    Column(
        modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        numpadItems.chunked(3).forEach { chunk ->
            ButtonGroup(
                overflowIndicator = {
                    // not so important since item will fit :)
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = "Localized description"
                    )
                },
                modifier = Modifier.padding(bottom = 8.dp),
            ) {
                for (i in chunk.indices) {
                    customItem(
                        buttonGroupContent = {
                            if (chunk[i].isNotEmpty()) {
                                val x = remember { MutableInteractionSource() }
                                Button(
                                    onClick = {},
                                    interactionSource = x,
                                    modifier = Modifier
                                        .animateWidth(x)
                                        .weight(1f)
                                        .height(72.dp),
                                    shape = MaterialTheme.shapes.extraLarge,
                                ) {
                                    Text(chunk[i])
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(72.dp)
                                )
                            }
                        },
                        menuContent = {
                            Box(Modifier)
                        },
                    )
                }
            }
        }
    }
}