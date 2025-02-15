package com.ilyasipek.composeanimations101.samples

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ilyasipek.composeanimations101.animations.ScrambleConfig
import com.ilyasipek.composeanimations101.animations.ScrambleTextAnimation
import com.ilyasipek.composeanimations101.utils.horizontalPadding
import kotlinx.serialization.Serializable

@Serializable
object ScrambleTextAnimationSampleScreen

@Composable
fun ScrambleTextAnimationSampleScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = { SampleToolbar("Scramble Text Animation", onBackClick = onBackClick) }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var text by remember {
                    mutableStateOf(generateRandomPassword(16))
                }

                Text(
                    "Random Password Generator", fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                )

                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalPadding(16.dp)
                        .padding(top = 24.dp),
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ScrambleTextAnimation(
                            text,
                            modifier = Modifier.weight(1f)
                        )

                        Box(
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(8.dp)
                                )
                                .padding(vertical = 4.dp, horizontal = 8.dp)
                        ) {
                            Text(
                                "Very strong",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        text = generateRandomPassword(16)
                    },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text("Generate")
                }
            }
        }

    }
}

// the most unreliable password generation algorithm ever.
private fun generateRandomPassword(
    length: Int
): String = List(length) { ScrambleConfig.DEFAULT_MASK.random() }.joinToString(separator = "")