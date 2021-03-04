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

sealed class DecreaseResult<T>(open val value: T) {
    data class Success<T>(override val value: T) : DecreaseResult<T>(value)
    data class ReachedZero<T>(override val value: T) : DecreaseResult<T>(value)
    data class AlreadyZero<T>(override val value: T) : DecreaseResult<T>(value)
}

val ZERO = ClockNumber(0)
val ZERO_MINUTES = ClockMinutes(minutes = ZERO, seconds = ZERO)

fun ClockMinutes.decrease(): DecreaseResult<ClockMinutes> {
    if (minutes.value == 0 && seconds.value == 0) {
        return DecreaseResult.AlreadyZero(ZERO_MINUTES)
    }
    if (seconds.value != 0) {
        return if (seconds.value - 1 > 0 || minutes.value > 0) {
            DecreaseResult.Success(
                ClockMinutes(
                    minutes = this.minutes,
                    seconds = ClockNumber(this.seconds.value - 1),
                )
            )
        } else {
            DecreaseResult.ReachedZero(ZERO_MINUTES)
        }
    }
    return if (minutes.value > 0) {
        DecreaseResult.Success(
            ClockMinutes(
                minutes = ClockNumber(minutes.value - 1),
                seconds = ClockNumber(59)
            )
        )
    } else {
        DecreaseResult.AlreadyZero(ZERO_MINUTES)
    }
}

fun ClockNumber.decrease(): DecreaseResult<ClockNumber> {
    if (value == 0) return DecreaseResult.AlreadyZero(ZERO)
    return if (value - 1 > 0) {
        DecreaseResult.Success(ClockNumber(value - 1))
    } else {
        DecreaseResult.ReachedZero(ZERO)
    }
}
