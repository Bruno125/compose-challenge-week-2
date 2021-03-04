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

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.composables.Colon
import com.example.androiddevchallenge.ui.composables.TwoDigitsClock
import com.example.androiddevchallenge.ui.composables.boardBorderAnimation
import com.example.androiddevchallenge.ui.composables.bouncySizeAnimation
import com.example.androiddevchallenge.ui.composables.shakeAnimation
import com.example.androiddevchallenge.ui.models.ClockMinutes
import com.example.androiddevchallenge.ui.models.ClockStateHolder
import com.example.androiddevchallenge.ui.theme.ClockTheme
import com.example.androiddevchallenge.ui.theme.RedClockTheme
import com.example.androiddevchallenge.ui.theme.YellowClockTheme
import kotlinx.coroutines.CoroutineScope

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val coroutineScope = rememberCoroutineScope()
    val stateHolder = remember { ClockStateHolder() }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundArena()
        TimeBoard(
            stateHolder,
            modifier = Modifier.align(BiasAlignment(0f, -0.5f))
        )
        ActionButtons(
            stateHolder, coroutineScope,
            modifier = Modifier
                .fillMaxWidth()
                .align(BiasAlignment(0f, 0.2f))
                .padding(16.dp)
        )
    }
}

@Composable
fun BackgroundArena() {
    Image(
        painter = painterResource(id = R.drawable.arena),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds,
    )
}

@Composable
fun TimeBoard(stateHolder: ClockStateHolder, modifier: Modifier = Modifier) {
    val timeLeft = stateHolder.timeLeft
    val shotClock = stateHolder.shotClock

    val boardBorder = boardBorderAnimation(shotClock.value)
    val boardRotation = shakeAnimation(shotClock.value == 0)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .width(300.dp)
            .height(220.dp)
            .rotate(boardRotation.value)
            .border(4.dp, boardBorder.value, RoundedCornerShape(16.dp))
            .padding(3.dp)
            .border(4.dp, Color.White, RoundedCornerShape(16.dp))
            .background(ClockTheme.colors.background)
            .padding(20.dp)
    ) {

        YellowClockTheme {
            MinutesClock(
                clock = timeLeft,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        RedClockTheme {
            val sizeMultiplier = bouncySizeAnimation(shotClock.value)
            val rotation = shakeAnimation(shake = shotClock.value == 24)
            Box(Modifier.size(130.dp)) {
                TwoDigitsClock(
                    number = shotClock,
                    modifier = Modifier
                        .size((110 * sizeMultiplier.value).dp)
                        .align(Alignment.Center)
                        .rotate(rotation.value)
                )
            }
        }
    }
}

@Composable
fun MinutesClock(clock: ClockMinutes, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.background(ClockTheme.colors.background),
        horizontalArrangement = Arrangement.Center,
    ) {
        TwoDigitsClock(number = clock.minutes, modifier = Modifier.size(60.dp))
        Colon(
            modifier = Modifier
                .width(18.dp)
                .height(60.dp)
        )
        TwoDigitsClock(number = clock.seconds, modifier = Modifier.size(60.dp))
    }
}

@Composable
fun ActionButtons(
    stateHolder: ClockStateHolder,
    coroutineScope: CoroutineScope,
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            imageVector = Icons.Default.Replay,
            contentDescription = "Reset",
            onClick = { stateHolder.resetShotClock(coroutineScope) }
        )

        if (stateHolder.isRunning) {
            ActionButton(
                imageVector = Icons.Default.Pause,
                contentDescription = "Pause",
                onClick = { stateHolder.stop() }
            )
        } else {
            ActionButton(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                onClick = { stateHolder.start(coroutineScope) }
            )
        }
    }
}

@Composable
fun ActionButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = ClockTheme.colors.secondary,
        contentColor = ClockTheme.colors.onSecondary,
    ) {
        Icon(imageVector = imageVector, contentDescription)
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun PortraitPreview() {
    MyApp()
}

@Preview("Dark Theme", widthDp = 640, heightDp = 360)
@Composable
fun LandscapePreview() {
    MyApp()
}
