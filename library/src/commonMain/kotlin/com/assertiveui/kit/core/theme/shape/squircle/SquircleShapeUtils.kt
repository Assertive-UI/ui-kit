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
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.unit.LayoutDirection
import com.assertiveui.kit.core.utils.transformFraction

/**
 * Creates an outline for a [SquircleShape], considering corner radii, smoothing, and layout direction.
 *
 * @param size The overall size of the shape as a [Size] object.
 * @param topStart The radius of the top-start corner in pixels. In left-to-right layouts, this corresponds to the top-left corner.
 * @param topEnd The radius of the top-end corner in pixels. In left-to-right layouts, this corresponds to the top-right corner.
 * @param bottomEnd The radius of the bottom-end corner in pixels. In left-to-right layouts, this corresponds to the bottom-right corner.
 * @param bottomStart The radius of the bottom-start corner in pixels. In left-to-right layouts, this corresponds to the bottom-left corner.
 * @param cornerSmoothing A float value between 0 and 100 that determines the smoothing of the corners.
 *                        A value of 0 represents no smoothing, while 100 represents fully smooth transitions.
 * @param layoutDirection The layout direction of the shape, specified as [LayoutDirection.Ltr] or [LayoutDirection.Rtl].
 *                        This affects how the corner radii are applied.
 * @return An [Outline] object representing the shape's boundary:
 *         - Returns a rectangle if all corner radii are zero.
 *         - Returns a generic path representing the squircle shape if any corner radius is non-zero.
 *
 * ##### Behavior:
 * - Clamps the corner radii to ensure they fit within the size of the shape.
 * - Adjusts corner radii based on the layout direction, swapping left and right corners in RTL layouts.
 * - Applies corner smoothing using the provided `cornerSmoothing` parameter to create a squircle-like effect.
 */
@Stable
internal fun createSquircleShapeOutline(
    size: Size,
    topStart: Float,
    topEnd: Float,
    bottomEnd: Float,
    bottomStart: Float,
    cornerSmoothing: Int,
    layoutDirection: LayoutDirection
): Outline = if (topStart + topEnd + bottomEnd + bottomStart == 0.0f) {
    Outline.Rectangle(size.toRect())
} else {

    val isLtr = layoutDirection == LayoutDirection.Ltr
    val topLeftCorner = clampedCornerRadius(if (isLtr) topStart else topEnd, size)
    val topRightCorner = clampedCornerRadius(if (isLtr) topEnd else topStart, size)
    val bottomLeftCorner = clampedCornerRadius(if (isLtr) bottomStart else bottomEnd, size)
    val bottomRightCorner = clampedCornerRadius(if (isLtr) bottomEnd else bottomStart, size)

    Outline.Generic(
        path = squircleShapePath(
            size = size,
            topLeftCorner = topLeftCorner,
            topRightCorner = topRightCorner,
            bottomLeftCorner = bottomLeftCorner,
            bottomRightCorner = bottomRightCorner,
            cornerSmoothing = clampedCornerSmoothing(cornerSmoothing)
        )
    )

}

/**
 *
 *  Clamps the corner radius from 0.0f to the size of the smallest axis.
 *
 *  @param cornerSize The corner radius in pixels.
 *  @param size The size of the shape.
 *
 **/
@Stable
internal fun clampedCornerRadius(
    cornerSize: Float,
    size: Size
): Float {
    val smallestAxis = size.minDimension / 2
    return cornerSize.coerceIn(0.0f, smallestAxis)
}

/**
 *
 *  Clamps the corner smoothing in the range 0..100.
 *
 *  @param cornerSmoothing (0 - no smoothing, 100 - maximum smoothing).
 *
 **/
@Stable
internal fun clampedCornerSmoothing(
    cornerSmoothing: Int
): Int = cornerSmoothing.coerceIn(0, 100)

/**
 * Transforms an [Int] based corner smoothing value to a [Float] value.
 *
 * @param smoothing The corner smoothing value.
 *
 * @return The transformed smoothing value.
 */
@Stable
internal fun convertIntBasedSmoothingToFloat(
    smoothing: Int
): Float = transformFraction(
    value = smoothing.toFloat(),
    startX = 0f,
    endX = 100f,
    startY = .55f,
    endY = 1f
)