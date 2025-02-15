package com.ilyasipek.composeanimations101

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyasipek.composeanimations101.samples.SampleToolbar
import com.ilyasipek.composeanimations101.samples.ShakeModifierSampleScreen
import kotlinx.serialization.Serializable

@Serializable
object MainScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier, onNavigateTo: (Any) -> Unit) {
    Scaffold(
        topBar = {
            SampleToolbar(title = "Dope Animations")
        }
    ) {
        MainContent(
            modifier = Modifier.padding(it),
            onNavigateTo = onNavigateTo
        )
    }
}

@Composable
fun MainContent(
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
                text = "Shake Animation",
                onClick = {
                    onNavigateTo(ShakeModifierSampleScreen)
                }
            )
        }
        item(span = { GridItemSpan(2) }) {
            Column {
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                Text(
                    text = "All animation samples will be added soon.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun SampleItem(text: String, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        modifier = Modifier.height(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text)
        }
    }
}