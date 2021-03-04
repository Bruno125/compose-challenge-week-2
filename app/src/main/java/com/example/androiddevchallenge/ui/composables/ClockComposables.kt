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

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import com.example.androiddevchallenge.ui.models.ClockNumber
import com.example.androiddevchallenge.ui.models.Digit
import com.example.androiddevchallenge.ui.models.DrawArea
import com.example.androiddevchallenge.ui.models.GridArea
import com.example.androiddevchallenge.ui.models.Side
import com.example.androiddevchallenge.ui.theme.ClockColors
import com.example.androiddevchallenge.ui.theme.ClockTheme

@Composable
fun TwoDigitsClock(number: ClockNumber, modifier: Modifier = Modifier) {
    val colors = ClockTheme.colors
    Canvas(modifier) {
        val drawAreaSize = size.minDimension
        val drawArea = DrawArea(
            size = Size(drawAreaSize, drawAreaSize),
            topLeft = Offset((size.width - drawAreaSize) / 2f, (size.height - drawAreaSize) / 2f),
            gridSize = 80
        )
        val numberSize = GridArea(
            horizontalCells = 36,
            verticalCells = 64,
        )
        val leftNumberArea = numberSize.copy(
            offset = GridArea(
                verticalCells = 7,
                horizontalCells = 0
            )
        )
        val rightNumberArea = numberSize.copy(
            offset = GridArea(
                verticalCells = 7,
                horizontalCells = leftNumberArea.horizontalCells + 6
            )
        )
        drawNumber(number.firstDigit, leftNumberArea, drawArea, colors)
        drawNumber(number.secondDigit, rightNumberArea, drawArea, colors)
    }
}

fun DrawScope.drawNumber(digit: Digit, gridArea: GridArea, drawArea: DrawArea, colors: ClockColors) {
    val areaTopLeft = Offset(
        x = drawArea.topLeft.x + drawArea.cells(gridArea.offset?.horizontalCells ?: 0),
        y = drawArea.topLeft.y + drawArea.cells(gridArea.offset?.verticalCells ?: 0),
    )

    fun drawHorizontalSide(isActive: Boolean) {
        drawSideWithCircles(
            gridArea = GridArea(verticalCells = 6, horizontalCells = 26),
            offset = areaTopLeft,
            cellSize = drawArea.cellSize,
            isActive = isActive,
            clockColors = colors,
        )
    }

    fun drawVerticalSide(isActive: Boolean) {
        drawSideWithCircles(
            gridArea = GridArea(verticalCells = 26, horizontalCells = 6),
            offset = areaTopLeft,
            cellSize = drawArea.cellSize,
            isActive = isActive,
            clockColors = colors,
        )
    }

    Side.values().forEach { side ->
        val isActive = digit.activeSides.contains(side)
        when (side) {
            Side.Top ->
                translate(left = drawArea.cells(5)) {
                    drawHorizontalSide(isActive)
                }
            Side.TopLeft ->
                translate(top = drawArea.cells(5)) {
                    drawVerticalSide(isActive)
                }
            Side.TopRight ->
                translate(top = drawArea.cells(5), left = drawArea.cells(30)) {
                    drawVerticalSide(isActive)
                }
            Side.Center ->
                translate(left = drawArea.cells(5), top = drawArea.cells(29)) {
                    drawHorizontalSide(isActive)
                }
            Side.BottomLeft ->
                translate(top = drawArea.cells(34)) {
                    drawVerticalSide(isActive)
                }
            Side.BottomRight ->
                translate(top = drawArea.cells(34), left = drawArea.cells(30)) {
                    drawVerticalSide(isActive)
                }
            Side.Bottom ->
                translate(left = drawArea.cells(5), top = drawArea.cells(58)) {
                    drawHorizontalSide(isActive)
                }
        }
    }
}

fun DrawScope.drawSideWithCircles(
    isActive: Boolean,
    gridArea: GridArea,
    offset: Offset,
    cellSize: Float,
    clockColors: ClockColors,
) {
    fun drawCircle(x: Int, y: Int) {
        val circleOffset = Offset(
            x = offset.x + x * cellSize - cellSize / 2,
            y = offset.y + y * cellSize - cellSize / 2,
        )
        drawOval(
            color = if (isActive) clockColors.on else clockColors.off,
            topLeft = circleOffset,
            size = Size(cellSize * 3, cellSize * 3)
        )
        drawOval(
            color = if (isActive) clockColors.light else clockColors.light.copy(alpha = 0.3f),
            topLeft = Offset(
                x = circleOffset.x + cellSize,
                y = circleOffset.y + cellSize,
            ),
            size = Size(cellSize, cellSize)
        )
    }

    if (gridArea.horizontalCells > gridArea.verticalCells) { // horizontal side
        repeat(6) { drawCircle(x = 2 + 4 * it, y = 0) }
        repeat(6) { drawCircle(x = 2 + 4 * it, y = 4) }
        drawCircle(x = 0, y = 2)
        drawCircle(x = 24, y = 2)
    } else { // vertical side
        repeat(6) { drawCircle(y = 2 + 4 * it, x = 0) }
        repeat(6) { drawCircle(y = 2 + 4 * it, x = 4) }
        drawCircle(y = 0, x = 2)
        drawCircle(y = 24, x = 2)
    }
}

@Composable
fun Colon(modifier: Modifier) {
    val colors = ClockTheme.colors
    Canvas(modifier) {
        val drawArea = DrawArea(
            size = Size(size.width, size.height),
            topLeft = Offset(0f, 0f),
            gridSize = 24
        )
        val cellSize = drawArea.cellSize

        fun drawCircle(x: Int, y: Int) {
            val circleOffset = Offset(
                x = x * cellSize - cellSize / 2,
                y = y * cellSize - cellSize / 2,
            )
            drawOval(
                color = colors.on,
                topLeft = circleOffset,
                size = Size(cellSize * 3, cellSize * 3)
            )
            drawOval(
                color = colors.light,
                topLeft = Offset(
                    x = circleOffset.x + cellSize,
                    y = circleOffset.y + cellSize,
                ),
                size = Size(cellSize, cellSize)
            )
        }

        drawCircle(x = 8, y = 20)
        drawCircle(x = 12, y = 20)
        drawCircle(x = 8, y = 24)
        drawCircle(x = 12, y = 24)

        drawCircle(x = 8, y = 56)
        drawCircle(x = 12, y = 56)
        drawCircle(x = 8, y = 60)
        drawCircle(x = 12, y = 60)
    }
}
