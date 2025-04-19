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

import androidx.compose.foundation.shape.RoundedCornerShape

/** Collection of commonly used corner smoothing values for a [SquircleShape]. */
object CornerSmoothing {

    /** Does not apply corner smoothing. The result will be [RoundedCornerShape]. */
    val None: Int get() = 0

    /** Applies a small amount of corner smoothing, resulting slightly pronounced [SquircleShape]. */
    val Small: Int get() = 20

    /** Applies a medium amount of corner smoothing, resulting quite pronounced [SquircleShape]. */
    val Medium: Int get() = 45

    /** Applies a high amount of corner smoothing, resulting highly pronounced [SquircleShape]. */
    val High: Int get() = 67

    /** Applies a full amount of corner smoothing, resulting fully pronounced [SquircleShape]. */
    val Full: Int get() = 100

}