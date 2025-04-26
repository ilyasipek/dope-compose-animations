package com.ilyasipek.composeanimations101.animations.wavingcircle

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged

// The Perlin noise fragment shader code
private const val NOISE_SHADER_SRC = """

float rand(float2 n) { 
	return fract(sin(dot(n, float2(12.9898, 4.1414))) * 43758.5453);
}

float noise(float2 p){
	float2 ip = floor(p);
	float2 u = fract(p);
	u = u*u*(3.0-2.0*u);
	
	float res = mix(
		mix(rand(ip),rand(ip+float2(1.0,0.0)),u.x),
		mix(rand(ip+float2(0.0,1.0)),rand(ip+float2(1.0,1.0)),u.x),u.y);
	return res*res;
}

uniform float2 size;
uniform float time;
uniform shader composable;
uniform float speedX;
uniform float scale;
uniform float amplitude;

half4 main(float2 fragCoord) {
    float2 uv = fragCoord / size;
    float2 circleCenter = float2(0.5, 0.5);
    float radius = 0.48;

    float distanceFromCenter = distance(uv, circleCenter);
    float2 animatedCoord = uv * scale + float2(time * speedX);
    float noiseFactor = noise(animatedCoord);
    float noisyRadius = radius + amplitude * (noiseFactor - 0.5);

    if (distanceFromCenter <= noisyRadius) {
        return half4(composable.eval(fragCoord).rgb, 1);
    } else {
        return half4(0.0, 0.0, 0.0, 0.0);
    }
}
"""

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun WavingCircle(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    noiseProperties: NoiseProperties = WavingCircleDefaults.noiseProperties(),
) {
    require(noiseProperties.speedX >= 0f) {
        "speedX cannot be less than 0f"
    }

    val shader = remember { RuntimeShader(NOISE_SHADER_SRC) }

    val time by produceState(0f) {
        while (true) {
            withInfiniteAnimationFrameMillis {
                value = it / 1000f
            }
        }
    }

    Canvas(
        modifier = modifier
            .onSizeChanged {
                shader.setFloatUniform("size", it.width.toFloat(), it.height.toFloat())
            }
            .graphicsLayer {
                clip = true
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
                shader.setFloatUniform("time", time)
                shader.setFloatUniform("speedX", noiseProperties.speedX)
                shader.setFloatUniform("scale", noiseProperties.scale)
                shader.setFloatUniform("amplitude", noiseProperties.amplitude)
            }
    ) {
        drawCircle(color = color)
    }

}