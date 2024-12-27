package com.ilyasipek.composeanimations101.utils

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

fun Modifier.horizontalPadding(
    padding: Dp
): Modifier = this.padding(horizontal = padding)

fun Modifier.verticalPadding(
    padding: Dp
): Modifier = this.padding(vertical = padding)