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
fun TowOptionsToggleAnimatedContent(
    modifier: Modifier = Modifier
) {

    var isMute by remember {
        mutableStateOf(false)
    }
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalPadding(16.dp),
        onClick = {
            isMute = !isMute
        }
    ) {
        AnimatedContent(
            isMute,
            transitionSpec = {
                // down down
                if (targetState) { // unmute to mute
                    slideInVertically() + scaleIn(initialScale = 0.5f) + fadeIn() togetherWith slideOutVertically(
                        targetOffsetY = { it / 2 },
                    ) + scaleOut(targetScale = 0.5f) + fadeOut()
                } else { // mute to unmute // up up
                    slideInVertically(initialOffsetY = { it / 2 }) + scaleIn(initialScale = 0.5f) + fadeIn() togetherWith slideOutVertically() + scaleOut(
                        targetScale = 0.5f
                    ) + fadeOut()
                }.using(SizeTransform(clip = false))
            }
        ) { targetState ->
            Text(
                text = if (targetState) "MUTE" else "UNMUTE",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
            )
        }
    }
}
