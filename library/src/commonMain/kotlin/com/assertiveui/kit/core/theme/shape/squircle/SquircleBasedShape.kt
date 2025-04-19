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

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize

/**
 *
 *  Base class for creating a Squircle shape derived from a [CornerBasedShape]
 *  defined by four corners and a corner smoothing.
 *
 *  @param topStart The top start corner radius defined as [CornerSize].
 *  @param topEnd The top end corner radius defined as [CornerSize].
 *  @param bottomStart The bottom start corner radius defined as [CornerSize].
 *  @param bottomEnd The bottom end corner radius defined as [CornerSize].
 *  @param cornerSmoothing The smoothing factor of the corners in the range 0..100.
 *
 **/
abstract class SquircleBasedShape(
    topStart: CornerSize,
    topEnd: CornerSize,
    bottomStart: CornerSize,
    bottomEnd: CornerSize,
    val cornerSmoothing: Int
) : CornerBasedShape(
    topStart = topStart,
    topEnd = topEnd,
    bottomStart = bottomStart,
    bottomEnd = bottomEnd
)