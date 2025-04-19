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

package com.assertiveui.kit.core.theme.color.utils

/**
 * A helpful utility for generating a hue offset.
 */
enum class HueOffsetDirection {

    /**
     * A constant used for generating a hue that is lower than the base hue.
     * E.g if the base hue is 210 and the hue difference is 70, the accent hue will be 140.
     */
    DOWN,

    /**
     * A constant used for generating a hue that is higher than the base hue.
     * E.g if the base hue is 140 and the hue difference is 70, the accent hue will be 210.
     */
    UP;

}