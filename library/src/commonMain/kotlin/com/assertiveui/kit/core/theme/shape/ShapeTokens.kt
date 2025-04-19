/*
 * Copyright 2024 Assertive UI (assertiveui.com)
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

package com.assertiveui.kit.core.theme.shape

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import sv.lib.squircleshape.CornerSmoothing
import sv.lib.squircleshape.SquircleShape

internal object DefaultShapeTokens : ShapeTokens {
    override val largeShape: Shape get() = SquircleShape(48.dp, CornerSmoothing.Small)
    override val mediumShape: Shape get() = SquircleShape(24.dp, CornerSmoothing.Small)
    override val smallShape: Shape get() = SquircleShape(16.dp, CornerSmoothing.Small)
}

@Immutable
internal interface ShapeTokens {
    val largeShape: Shape
    val mediumShape: Shape
    val smallShape: Shape
}