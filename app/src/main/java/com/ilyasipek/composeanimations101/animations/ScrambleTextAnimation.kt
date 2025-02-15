package com.ilyasipek.composeanimations101.animations

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.ilyasipek.composeanimations101.extension.replaceIndex
import com.ilyasipek.composeanimations101.samples.ScrambleTextAnimationSampleScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @param scrambleIterations the number or rotations of each char while animating
 * @param preserveSpaces keeps an empty char for empty chars in scrambled text (not when animating tho)
 * */
data class ScrambleConfig(
    val maskCharacters: String = DEFAULT_MASK,
    val scrambleDuration: Long = 25,
    val scrambleIterations: Int = 3,
    val preserveSpaces: Boolean = true,
) {
    val randomMaskChar: Char
        get() = maskCharacters.random()

    companion object {
        const val DEFAULT_MASK =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#\$%^&*()_+"
    }
}

@Composable
fun ScrambleTextAnimation(
    text: String,
    modifier: Modifier = Modifier,
    config: ScrambleConfig = ScrambleConfig(),
    textStyle: TextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontSize = 20.sp,
    )
) {
    var scrambledText by remember(text, config) {
        mutableStateOf(
            List(text.length) {
                if (text[it] == ' ') " " else config.maskCharacters.random()
            }.joinToString("")
        )
    }

    LaunchedEffect(text, config) {
        text.forEachIndexed { index, c ->
            launch {
                repeat(index + config.scrambleIterations) {
                    scrambledText = scrambledText.replaceIndex(
                        index, config.randomMaskChar
                    )
                    delay(config.scrambleDuration)
                }
                scrambledText = scrambledText.replaceIndex(
                    index, c
                )
                delay(config.scrambleDuration)
            }
        }
    }

    Text(
        text = scrambledText,
        modifier = modifier,
        style = textStyle
    )
}

@Preview
@Composable
private fun ScrambleTextAnimationPreview() {
    ScrambleTextAnimation(text = "Password to Scramble")
}

@Preview
@Composable
private fun ScrambleTextAnimationSample() {
    // check full example here
    ScrambleTextAnimationSampleScreen(
        onBackClick = {}
    )
}