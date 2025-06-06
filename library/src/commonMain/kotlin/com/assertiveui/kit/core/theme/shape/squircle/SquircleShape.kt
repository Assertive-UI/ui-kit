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

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Stable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

/**
 *
 *  Creates a [SquircleShape] with corner radius percent defined as [Int] value.
 *
 *  @param percent The corner radius percent from 0 to 100.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    percent: Int = 100,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(percent),
    topEndCorner = CornerSize(percent),
    bottomStartCorner = CornerSize(percent),
    bottomEndCorner = CornerSize(percent),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleShape] with corner radius defined as [Dp] value.
 *
 *  @param radius The corner radius.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    radius: Dp,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(radius),
    topEndCorner = CornerSize(radius),
    bottomStartCorner = CornerSize(radius),
    bottomEndCorner = CornerSize(radius),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleShape] with corner radius in pixels defined as [Float] value.
 *
 *  @param radius The corner radius in pixels.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    radius: Float,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(radius),
    topEndCorner = CornerSize(radius),
    bottomStartCorner = CornerSize(radius),
    bottomEndCorner = CornerSize(radius),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleShape] with corners percent defined as [Int] values.
 *
 *  @param topStart The top start corner radius percent from 0 to 100.
 *  @param topEnd The top end corner radius percent from 0 to 100.
 *  @param bottomStart The bottom start corner radius percent from 0 to 100.
 *  @param bottomEnd The bottom end corner radius percent from 0 to 100.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    topStart: Int = 0,
    topEnd: Int = 0,
    bottomStart: Int = 0,
    bottomEnd: Int = 0,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(topStart),
    topEndCorner = CornerSize(topEnd),
    bottomStartCorner = CornerSize(bottomStart),
    bottomEndCorner = CornerSize(bottomEnd),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleShape] with corners defined as [Dp] values.
 *
 *  @param topStart The top start corner radius.
 *  @param topEnd The top end corner radius.
 *  @param bottomStart The bottom start corner radius.
 *  @param bottomEnd The bottom end corner radius.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(topStart),
    topEndCorner = CornerSize(topEnd),
    bottomStartCorner = CornerSize(bottomStart),
    bottomEndCorner = CornerSize(bottomEnd),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleShape] with pixel corners defined as [Float] values.
 *
 *  @param topStart The top start corner radius in pixels.
 *  @param topEnd The top end corner radius.
 *  @param bottomStart The bottom start corner radius.
 *  @param bottomEnd The bottom end corner radius.
 *  @param cornerSmoothing The corner smoothing factor from 0 to 100.
 *
 **/
fun SquircleShape(
    topStart: Float = 0f,
    topEnd: Float = 0f,
    bottomStart: Float = 0f,
    bottomEnd: Float = 0f,
    cornerSmoothing: Int = CornerSmoothing.Medium
) = SquircleShape(
    topStartCorner = CornerSize(topStart),
    topEndCorner = CornerSize(topEnd),
    bottomStartCorner = CornerSize(bottomStart),
    bottomEndCorner = CornerSize(bottomEnd),
    cornerSmoothing = cornerSmoothing
)

/**
 *
 *  Creates a [SquircleBasedShape].
 *
 *  @param topStartCorner The top start corner radius defined as [CornerSize].
 *  @param topEndCorner The top end corner radius defined as [CornerSize].
 *  @param bottomStartCorner The bottom start corner radius defined as [CornerSize].
 *  @param bottomEndCorner The bottom end corner radius defined as [CornerSize].
 *  @param cornerSmoothing The corner cornerSmoothing from 0 to 100.
 *
 **/
@Stable
class SquircleShape(
    topStartCorner: CornerSize,
    topEndCorner: CornerSize,
    bottomStartCorner: CornerSize,
    bottomEndCorner: CornerSize,
    cornerSmoothing: Int
) : SquircleBasedShape(
    topStart = topStartCorner,
    topEnd = topEndCorner,
    bottomStart = bottomStartCorner,
    bottomEnd = bottomEndCorner,
    cornerSmoothing = cornerSmoothing
) {

    override fun copy(
        topStart: CornerSize,
        topEnd: CornerSize,
        bottomEnd: CornerSize,
        bottomStart: CornerSize
    ) = SquircleShape(
        topStartCorner = topStart,
        topEndCorner = topEnd,
        bottomStartCorner = bottomStart,
        bottomEndCorner = bottomEnd,
        cornerSmoothing = cornerSmoothing
    )

    override fun createOutline(
        size: Size,
        topStart: Float,
        topEnd: Float,
        bottomEnd: Float,
        bottomStart: Float,
        layoutDirection: LayoutDirection
    ) = createSquircleShapeOutline(
        size = size,
        topStart = topStart,
        topEnd = topEnd,
        bottomEnd = bottomEnd,
        bottomStart = bottomStart,
        cornerSmoothing = cornerSmoothing,
        layoutDirection = layoutDirection
    )

    override fun toString(): String {
        return "SquircleShape(" +
                "topStart = $topStart, " +
                "topEnd = $topEnd, " +
                "bottomStart = $bottomStart, " +
                "bottomEnd = $bottomEnd, " +
                "cornerSmoothing = $cornerSmoothing" +
                ")"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SquircleShape) return false
        if (topStart != other.topStart) return false
        if (topEnd != other.topEnd) return false
        if (bottomStart != other.bottomStart) return false
        if (bottomEnd != other.bottomEnd) return false
        if (cornerSmoothing != other.cornerSmoothing) return false
        return true
    }

    override fun hashCode(): Int {
        var result = topStart.hashCode()
        result = 31 * result + topEnd.hashCode()
        result = 31 * result + bottomStart.hashCode()
        result = 31 * result + bottomEnd.hashCode()
        result = 31 * result + cornerSmoothing.hashCode()
        return result
    }

}