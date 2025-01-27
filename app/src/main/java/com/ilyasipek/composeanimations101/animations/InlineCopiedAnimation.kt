package com.ilyasipek.composeanimations101.animations

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Podcasts
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.ui.theme.ComposeAnimations101Theme
import com.ilyasipek.composeanimations101.utils.clickable
import com.ilyasipek.composeanimations101.utils.horizontalPadding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun InlineCopiedAnimation(
    modifier: Modifier = Modifier
) {
    Card(
        modifier.height(48.dp),
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        ),
    ) {
        Row(
            Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxHeight(),
            verticalAlignment = CenterVertically,
        ) {
            Icon(
                Icons.Rounded.Podcasts,
                contentDescription = null,
                modifier = Modifier.padding(start = 8.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "z0GWOexOoIY0MdMMz0",
                Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            CopyIcon()
        }
    }
}

@Composable
private fun CopyIcon(
    modifier: Modifier = Modifier
) {
    var showCopied by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier
            .padding(start = 16.dp)
            .clickable(
                enabled = !showCopied,
                rippleEnabled = false
            ) {
                coroutineScope.launch {
                    showCopied = true
                    delay(2000)
                    showCopied = false
                }
            },
        shape = CircleShape,
        color = MaterialTheme.colorScheme.onBackground,
        contentColor = MaterialTheme.colorScheme.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy
                    )
                ),
            verticalAlignment = CenterVertically
        ) {
            Icon(
                Icons.Rounded.ContentCopy,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )

            if (showCopied) {
                Text(
                    "Copied!",
                    modifier = Modifier.horizontalPadding(4.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InlineCopiedAnimationPreview() {
    ComposeAnimations101Theme {
        InlineCopiedAnimation(
            modifier = Modifier.padding(16.dp)
        )
    }
}