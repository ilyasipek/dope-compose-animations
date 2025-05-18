package com.ilyasipek.composeanimations101.samples.m3expressive

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.SampleItem
import com.ilyasipek.composeanimations101.samples.SampleToolbar
import kotlinx.serialization.Serializable

@Serializable
object M3ExpressivePlaygroundSampleScreen

@Composable
fun M3ExpressivePlaygroundSampleScreen(
    onBackClick: () -> Unit,
    onNavigateTo: (Any) -> Unit
) {
    Scaffold(
        topBar = { SampleToolbar("M3 Expressive", onBackClick = onBackClick) }
    ) {
        Content(
            modifier = Modifier.padding(it),
            onNavigateTo = onNavigateTo
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    onNavigateTo: (Any) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            SampleItem(
                text = "Button Group",
                onClick = {
                    onNavigateTo(M3ExpressiveButtonGroupSampleScreen)
                }
            )
        }
        item {
            SampleItem(
                text = "Pull to refresh",
                onClick = {
                    onNavigateTo(M3EPullToRefreshSampleScreen)
                }
            )
        }
    }
}