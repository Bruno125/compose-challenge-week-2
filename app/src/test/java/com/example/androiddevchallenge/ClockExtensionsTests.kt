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
package com.example.androiddevchallenge

import com.example.androiddevchallenge.ui.models.ClockMinutes
import com.example.androiddevchallenge.ui.models.ClockNumber
import com.example.androiddevchallenge.ui.models.DecreaseResult
import com.example.androiddevchallenge.ui.models.decrease
import org.junit.Assert
import org.junit.Test

class ClockExtensionsTests {

    @Test
    fun test_reduce_to_previous_minute() {
        val minute = ClockMinutes(
            minutes = ClockNumber(12),
            seconds = ClockNumber(0),
        )
        minute.decrease() shouldEqual DecreaseResult.Success(
            ClockMinutes(
                minutes = ClockNumber(11),
                seconds = ClockNumber(59)
            )
        )
    }

    @Test
    fun test_reduce_to_previous_second() {
        val minute = ClockMinutes(
            minutes = ClockNumber(11),
            seconds = ClockNumber(59),
        )
        minute.decrease() shouldEqual DecreaseResult.Success(
            ClockMinutes(
                minutes = ClockNumber(11),
                seconds = ClockNumber(58)
            )
        )
    }

    @Test
    fun test_reduce_to_zero_with_minutes_left() {
        val minute = ClockMinutes(
            minutes = ClockNumber(11),
            seconds = ClockNumber(1),
        )
        minute.decrease() shouldEqual DecreaseResult.Success(
            ClockMinutes(
                minutes = ClockNumber(11),
                seconds = ClockNumber(0)
            )
        )
    }

    @Test
    fun test_reduce_to_previous_minute_no_minutes_left() {
        val minute = ClockMinutes(
            minutes = ClockNumber(1),
            seconds = ClockNumber(0),
        )
        minute.decrease() shouldEqual DecreaseResult.Success(
            ClockMinutes(
                minutes = ClockNumber(0),
                seconds = ClockNumber(59)
            )
        )
    }

    @Test
    fun test_reduce_time_with_minutes_to_zero() {
        val minute = ClockMinutes(
            minutes = ClockNumber(0),
            seconds = ClockNumber(1),
        )
        minute.decrease() shouldEqual DecreaseResult.ReachedZero(
            ClockMinutes(
                minutes = ClockNumber(0),
                seconds = ClockNumber(0)
            )
        )
    }

    @Test
    fun test_reduce_minutes_but_is_already_zero() {
        val minute = ClockMinutes(
            minutes = ClockNumber(0),
            seconds = ClockNumber(0),
        )
        minute.decrease() shouldEqual DecreaseResult.AlreadyZero(
            ClockMinutes(
                minutes = ClockNumber(0),
                seconds = ClockNumber(0)
            )
        )
    }

    @Test
    fun test_reduce_number_by_one() {
        val number = ClockNumber(24)
        number.decrease() shouldEqual DecreaseResult.Success(ClockNumber(23))
    }

    @Test
    fun test_reduce_number_to_zero() {
        val number = ClockNumber(1)
        number.decrease() shouldEqual DecreaseResult.ReachedZero(ClockNumber(0))
    }
    @Test
    fun test_reduce_number_but_is_already_zero() {
        val number = ClockNumber(0)
        number.decrease() shouldEqual DecreaseResult.AlreadyZero(ClockNumber(0))
    }

    private infix fun Any?.shouldEqual(expected: Any?) {
        Assert.assertEquals(expected, this)
    }
}
