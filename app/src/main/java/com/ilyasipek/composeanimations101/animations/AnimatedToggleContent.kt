package com.ilyasipek.composeanimations101.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.utils.horizontalPadding

@Composable
fun AnimatedToggleContent(
    isOn: Boolean,
    onContent: @Composable () -> Unit,
    offContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = isOn,
        modifier = modifier,
        transitionSpec = {
            if (targetState) {
                slideInVertically() + scaleIn(initialScale = 0.5f) + fadeIn() togetherWith
                        slideOutVertically(targetOffsetY = { it / 2 }) + scaleOut(targetScale = 0.5f) + fadeOut()
            } else {
                slideInVertically(initialOffsetY = { it / 2 }) + scaleIn(initialScale = 0.5f) + fadeIn() togetherWith
                        slideOutVertically() + scaleOut(targetScale = 0.5f) + fadeOut()
            }.using(SizeTransform(clip = false))
        },
        label = "AnimatedToggleSwitchContent",
    ) { targetState ->
        if (targetState) {
            onContent()
        } else {
            offContent()
        }
    }
}

// Example usage of AnimatedToggleContent
@Composable
fun MuteUnMuteToggle() {
    var isMute by remember {
        mutableStateOf(false)
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalPadding(16.dp)
            .padding(bottom = 16.dp),
        onClick = {
            isMute = !isMute
        },
        shape = RoundedCornerShape(8.dp)
    ) {
        AnimatedToggleContent(
            isOn = isMute,
            onContent = {
                Text(
                    text = "MUTE",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            },
            offContent = {
                Text(
                    text = "UNMUTE",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
        )
    }
}

