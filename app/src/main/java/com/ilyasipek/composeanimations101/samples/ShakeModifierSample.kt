package com.ilyasipek.composeanimations101.samples

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ilyasipek.composeanimations101.animations.modifiers.ShakeConfig
import com.ilyasipek.composeanimations101.animations.modifiers.rememberShakeController
import com.ilyasipek.composeanimations101.animations.modifiers.shake
import com.ilyasipek.composeanimations101.ui.theme.ComposeAnimations101Theme
import kotlinx.coroutines.launch

@Composable
fun ShakeModifierSample(
    modifier: Modifier = Modifier,
) {
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val shakeConfig = remember {
        ShakeConfig(
            iterations = 5,
            transitionX = 10f,
            transitionY = 0f
        )
    }
    val shakeController = rememberShakeController(shakeConfig)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                if (isError) isError = false
            },
            modifier = Modifier
                .fillMaxWidth()
                .shake(shakeController),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError,
            shape = CircleShape
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (isValidPassword(password)) {
                    Toast.makeText(context, "Password is valid!", Toast.LENGTH_SHORT).show()
                } else {
                    isError = true
                    Toast.makeText(context, "Password isn't valid!", Toast.LENGTH_SHORT).show()
                    scope.launch {
                        shakeController.shake()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit")
        }
    }
}

private fun isValidPassword(password: String): Boolean {
    return password.length >= 8 &&
            password.any { it.isUpperCase() } &&
            password.any { it.isLowerCase() } &&
            password.any { it.isDigit() }
}

@Preview(showBackground = true)
@Composable
private fun ShakeModifierSamplePreview() {
    ComposeAnimations101Theme {
        ShakeModifierSample()
    }
}