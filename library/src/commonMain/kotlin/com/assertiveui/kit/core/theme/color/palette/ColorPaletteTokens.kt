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

package com.assertiveui.kit.core.theme.color.palette

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

object LightColorPaletteTokens : ColorPaletteTokens {

    override val baseHue: Int get() = 210
    override val base: Color get() = Color(0xFF004A8A)
    override val onBase: Color get() = Color(0xFFE3fAFF)

    override val accentHue: Int get() = 140
    override val accent: Color get() = Color(0xFF004F2D)
    override val onAccent: Color get() = Color(0xFFECF8F3)

    override val surfaceElevationLow: Color get() = Color(0xFFDBE4E8)
    override val onSurfaceElevationLow: Color get() = Color(0xFF0D181D)

    override val surfaceElevationMedium: Color get() = Color(0xFFE7EDF0)
    override val onSurfaceElevationMedium: Color get() = Color(0xFF070E13)

    override val surfaceElevationHigh: Color get() = Color(0xFFF3F6F7)
    override val onSurfaceElevationHigh: Color get() = Color(0xFF030609)

    override val error: Color get() = Color(0xFF830012)
    override val onError: Color get() = Color(0xFFFFF2F1)

    override val outline: Color get() = Color(0xFFCFDBE1)

}

object DarkColorPaletteTokens : ColorPaletteTokens {

    override val baseHue: Int get() = 210
    override val base: Color get() = Color(0xFF00BEFF)
    override val onBase: Color get() = Color(0xFF000817)

    override val accentHue: Int get() = 140
    override val accent: Color get() = Color(0xFF00BB8D)
    override val onAccent: Color get() = Color(0xFF000903)

    override val surfaceElevationLow: Color get() = Color(0xFF070E13)
    override val onSurfaceElevationLow: Color get() = Color(0xFFDBE4E8)

    override val surfaceElevationMedium: Color get() = Color(0xFF070E13)
    override val onSurfaceElevationMedium: Color get() = Color(0xFFE7EDF0)

    override val surfaceElevationHigh: Color get() = Color(0xFF0D181D)
    override val onSurfaceElevationHigh: Color get() = Color(0xFFF3F6F7)

    override val error: Color get() = Color(0xFFFF7672)
    override val onError: Color get() = Color(0xFF160001)

    override val outline: Color get() = Color(0xFF070E13)

}

@Immutable
interface ColorPaletteTokens {
    val baseHue: Int
    val base: Color
    val onBase: Color
    val accentHue: Int
    val accent: Color
    val onAccent: Color
    val surfaceElevationLow: Color
    val onSurfaceElevationLow: Color
    val surfaceElevationMedium: Color
    val onSurfaceElevationMedium: Color
    val surfaceElevationHigh: Color
    val onSurfaceElevationHigh: Color
    val error: Color
    val onError: Color
    val outline: Color
}