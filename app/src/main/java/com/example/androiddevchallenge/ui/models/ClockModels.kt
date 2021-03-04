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
package com.example.androiddevchallenge.ui.models

data class ClockNumber(val value: Int) {
    init {
        if (value < 0 || value > 99) {
            throw IllegalAccessException("Clock only supports numbers with 2 digits")
        }
    }
    val firstDigit by lazy { DIGITS[value / 10]!! }
    val secondDigit by lazy { DIGITS[value % 10]!! }
}

data class ClockMinutes(
    val minutes: ClockNumber,
    val seconds: ClockNumber,
)

enum class Digit(val activeSides: Set<Side>) {
    Zero(
        setOf(
            Side.Top,
            Side.TopRight,
            Side.TopLeft,
            Side.BottomLeft,
            Side.BottomRight,
            Side.Bottom
        )
    ),
    One(setOf(Side.TopRight, Side.BottomRight)),
    Two(setOf(Side.Top, Side.TopRight, Side.Center, Side.BottomLeft, Side.Bottom)),
    Three(setOf(Side.Top, Side.TopRight, Side.Center, Side.BottomRight, Side.Bottom)),
    Four(setOf(Side.TopLeft, Side.TopRight, Side.Center, Side.BottomRight)),
    Five(setOf(Side.Top, Side.TopLeft, Side.Center, Side.BottomRight, Side.Bottom)),
    Six(setOf(Side.Top, Side.TopLeft, Side.Center, Side.BottomLeft, Side.BottomRight, Side.Bottom)),
    Seven(setOf(Side.Top, Side.TopRight, Side.BottomRight)),
    Eight(
        setOf(
            Side.Top,
            Side.TopLeft,
            Side.TopRight,
            Side.Center,
            Side.BottomLeft,
            Side.BottomRight,
            Side.Bottom
        )
    ),
    Nine(setOf(Side.Top, Side.TopLeft, Side.TopRight, Side.Center, Side.BottomRight)),
}

val DIGITS = hashMapOf(
    0 to Digit.Zero,
    1 to Digit.One,
    2 to Digit.Two,
    3 to Digit.Three,
    4 to Digit.Four,
    5 to Digit.Five,
    6 to Digit.Six,
    7 to Digit.Seven,
    8 to Digit.Eight,
    9 to Digit.Nine,
)

enum class Side {
    Top,
    TopLeft,
    TopRight,
    Center,
    BottomLeft,
    BottomRight,
    Bottom
}
