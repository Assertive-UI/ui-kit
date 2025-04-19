/*
 * Copyright 2025 Assertive UI (assertiveui.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.assertiveui.kit.core.theme.shape.squircle

import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path

/**
 *
 *  The path used for drawing a Squircle shape.
 *
 *  @param size The size of the shape in pixels.
 *  @param topLeftCorner The top left corner radius in pixels.
 *  @param topRightCorner The top right corner radius in pixels.
 *  @param bottomLeftCorner The bottom left corner radius in pixels.
 *  @param bottomRightCorner The bottom right corner radius in pixels.
 *  @param cornerSmoothing (0 - no smoothing, 100 - full smoothing).
 *
 **/
@Stable
internal fun squircleShapePath(
    size: Size,
    topLeftCorner: Float,
    topRightCorner: Float,
    bottomLeftCorner: Float,
    bottomRightCorner: Float,
    cornerSmoothing: Int = CornerSmoothing.Medium
): Path {

    val smoothingFactor = 1 - convertIntBasedSmoothingToFloat(cornerSmoothing)
    val width = size.width
    val height = size.height

    return Path().apply {

        moveTo(
            x = topLeftCorner,
            y = 0f
        )

        lineTo(
            x = width - topRightCorner,
            y = 0f
        )

        cubicTo(
            x1 = width - topRightCorner * smoothingFactor,
            y1 = 0f,
            x2 = width,
            y2 = topRightCorner * smoothingFactor,
            x3 = width,
            y3 = topRightCorner
        )

        lineTo(
            x = width,
            y = height - bottomRightCorner
        )

        cubicTo(
            x1 = width,
            y1 = height - bottomRightCorner * smoothingFactor,
            x2 = width - bottomRightCorner * smoothingFactor,
            y2 = height,
            x3 = width - bottomRightCorner,
            y3 = height
        )

        lineTo(
            x = bottomLeftCorner,
            y = height
        )

        cubicTo(
            x1 = bottomLeftCorner * smoothingFactor,
            y1 = height,
            x2 = 0f,
            y2 = height - bottomLeftCorner * smoothingFactor,
            x3 = 0f,
            y3 = height - bottomLeftCorner
        )

        lineTo(
            x = 0f,
            y = topLeftCorner
        )

        cubicTo(
            x1 = 0f,
            y1 = topLeftCorner * smoothingFactor,
            x2 = topLeftCorner * smoothingFactor,
            y2 = 0f,
            x3 = topLeftCorner,
            y3 = 0f
        )

        close()

    }

}