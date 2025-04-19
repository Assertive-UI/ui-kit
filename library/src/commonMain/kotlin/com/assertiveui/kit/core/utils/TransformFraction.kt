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

package com.assertiveui.kit.core.utils

/**
 * A utility function to transform a [Float] fraction value from one range to another.
 *
 * @param value The input value to be transformed.
 * @param startX The start of the input range.
 * @param endX The end of the input range.
 * @param startY The start of the output range.
 * @param endY The end of the output range.
 *
 * @return The transformed value.
 */
internal fun transformFraction(
    value: Float,
    startX: Float = 0f,
    endX: Float = 1f,
    startY: Float,
    endY: Float
): Float {

    // Check if startX is less than endX
    val newStartX = if (startX <= endX) startX else endX
    val newEndX = if (startX <= endX) endX else startX

    // Check if startY is less than endY
    val newStartY = if (startY <= endY) startY else endY
    val newEndY = if (startY <= endY) endY else startY

    // Transform the value to the new range
    return ((value - newStartX) / (newEndX - newStartX)) * (newEndY - newStartY) + newStartY

}
