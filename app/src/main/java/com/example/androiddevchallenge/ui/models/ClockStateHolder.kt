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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ClockStateHolder {

    var timeLeft by mutableStateOf(TWELVE_MINUTES)
        private set

    var shotClock by mutableStateOf(TWENTY_FOUR_SECONDS)
        private set

    var isRunning by mutableStateOf(false)
        private set

    private var timeLeftJob: Job? = null
    private var shotClockJob: Job? = null

    fun start(coroutineScope: CoroutineScope) {
        isRunning = true
        if (timeLeftJob == null) startTime(coroutineScope)
        if (shotClockJob == null) startShotClock(coroutineScope)
    }

    private fun startTime(coroutineScope: CoroutineScope) {
        timeLeftJob = coroutineScope.launch {
            while (true) {
                delay(1000)
                val result = timeLeft.decrease()
                timeLeft = result.value
                if (result !is DecreaseResult.Success) {
                    stop()
                    break
                }
            }
        }
    }

    private fun startShotClock(coroutineScope: CoroutineScope) {
        shotClockJob = coroutineScope.launch {
            while (true) {
                delay(1000)
                val result = shotClock.decrease()
                shotClock = result.value
                if (result !is DecreaseResult.Success) {
                    stop()
                    break
                }
            }
        }
    }

    fun stop() {
        timeLeftJob?.cancel()
        timeLeftJob = null

        shotClockJob?.cancel()
        shotClockJob = null

        isRunning = false
    }

    fun resetShotClock(coroutineScope: CoroutineScope) {
        shotClockJob?.invokeOnCompletion { startShotClock(coroutineScope) }
        shotClockJob?.cancel()
        shotClock = TWENTY_FOUR_SECONDS
    }

    companion object {
        private val TWELVE_MINUTES = ClockMinutes(
            minutes = ClockNumber(12),
            seconds = ClockNumber(0)
        )
        private val TWENTY_FOUR_SECONDS = ClockNumber(24)
    }
}
