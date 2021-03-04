/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.composables

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun bouncySizeAnimation(shotClock: Int): AnimationState<Float, AnimationVector1D> {
    val sizeMultiplier = remember { AnimationState(1f) }

    LaunchedEffect(shotClock) {
        if (shotClock <= 5) {
            sizeMultiplier.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 1000
                    0.85f at 300
                    1f at 600
                }
            )
        }
    }

    return sizeMultiplier
}

@Composable
fun shakeAnimation(shake: Boolean): AnimationState<Float, AnimationVector1D> {
    val sizeMultiplier = remember(shake) { AnimationState(0f) }

    LaunchedEffect(shake) {
        if (shake) {
            sizeMultiplier.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = 600
                    5f at 100
                    -5f at 200
                    5f at 300
                    -5f at 400
                    5f at 500
                }
            )
        }
    }

    return sizeMultiplier
}

@Composable
fun boardBorderAnimation(time: Int): Animatable<Color, AnimationVector4D> {
    val initial = Color.Gray
    val animation = remember { Animatable(initial) }

    LaunchedEffect(time) {
        if (time == 0) {
            animation.animateTo(
                targetValue = Color.Red,
                animationSpec = repeatable(3, tween(250), repeatMode = RepeatMode.Reverse)
            )
        } else {
            animation.animateTo(
                targetValue = initial,
                animationSpec = snap(0)
            )
        }
    }

    return animation
}
