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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

object ClockTheme {
    val colors: ClockColors
        @Composable
        @ReadOnlyComposable
        get() = LocalClockColors.current
}

internal val LocalClockColors = staticCompositionLocalOf { redClockColors() }

fun redClockColors() = ClockColors(
    background = Color.Black,
    on = Color(0xFFEE0125),
    off = Color(0x886F2A0F),
    light = Color.White,
    secondary = Color(0xFFFF6600),
    onSecondary = Color.White,
)

fun yellowClockColors() = ClockColors(
    background = Color.Black,
    on = Color(0xFFFBFF70),
    off = Color(0x886F2A0F),
    light = Color.White,
    secondary = Color(0xFFFF6600),
    onSecondary = Color.White,
)

@Composable
fun RedClockTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalClockColors provides redClockColors()) {
        content()
    }
}

@Composable
fun YellowClockTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalClockColors provides yellowClockColors()) {
        content()
    }
}
