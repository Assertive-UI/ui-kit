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

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class ColorPalette(
    val baseHue: Int,
    val base: Color,
    val onBase: Color,
    val accentHue: Int,
    val accent: Color,
    val onAccent: Color,
    val surfaceElevationLow: Color,
    val onSurfaceElevationLow: Color,
    val surfaceElevationMedium: Color,
    val onSurfaceElevationMedium: Color,
    val surfaceElevationHigh: Color,
    val onSurfaceElevationHigh: Color,
    val error: Color,
    val onError: Color,
    val outline: Color
)

/**
 * A function that returns a default dark [ColorPalette], with an optional overriding.
 */
@Stable
fun lightColorPalette(
    baseHue: Int = LightColorPaletteTokens.baseHue,
    base: Color = LightColorPaletteTokens.base,
    onBase: Color = LightColorPaletteTokens.onBase,
    accentHue: Int = LightColorPaletteTokens.accentHue,
    accent: Color = LightColorPaletteTokens.accent,
    onAccent: Color = LightColorPaletteTokens.onAccent,
    surfaceElevationLow: Color = LightColorPaletteTokens.surfaceElevationLow,
    onSurfaceElevationLow: Color = LightColorPaletteTokens.onSurfaceElevationLow,
    surfaceElevationMedium: Color = LightColorPaletteTokens.surfaceElevationMedium,
    onSurfaceElevationMedium: Color = LightColorPaletteTokens.onSurfaceElevationMedium,
    surfaceElevationHigh: Color = LightColorPaletteTokens.surfaceElevationHigh,
    onSurfaceElevationHigh: Color = LightColorPaletteTokens.onSurfaceElevationHigh,
    error: Color = LightColorPaletteTokens.error,
    onError: Color = LightColorPaletteTokens.onError,
    outline: Color = LightColorPaletteTokens.outline
) = ColorPalette(
    baseHue = baseHue,
    base = base,
    onBase = onBase,
    accentHue = accentHue,
    accent = accent,
    onAccent = onAccent,
    surfaceElevationLow = surfaceElevationLow,
    onSurfaceElevationLow = onSurfaceElevationLow,
    surfaceElevationMedium = surfaceElevationMedium,
    onSurfaceElevationMedium = onSurfaceElevationMedium,
    surfaceElevationHigh = surfaceElevationHigh,
    onSurfaceElevationHigh = onSurfaceElevationHigh,
    error = error,
    onError = onError,
    outline = outline
)

/**
 * A function that returns a default dark [ColorPalette], with an optional overriding.
 */
@Stable
fun darkColorPalette(
    baseHue: Int = DarkColorPaletteTokens.baseHue,
    base: Color = DarkColorPaletteTokens.base,
    onBase: Color = DarkColorPaletteTokens.onBase,
    accentHue: Int = DarkColorPaletteTokens.accentHue,
    accent: Color = DarkColorPaletteTokens.accent,
    onAccent: Color = DarkColorPaletteTokens.onAccent,
    surfaceElevationLow: Color = DarkColorPaletteTokens.surfaceElevationLow,
    onSurfaceElevationLow: Color = DarkColorPaletteTokens.onSurfaceElevationLow,
    surfaceElevationMedium: Color = DarkColorPaletteTokens.surfaceElevationMedium,
    onSurfaceElevationMedium: Color = DarkColorPaletteTokens.onSurfaceElevationMedium,
    surfaceElevationHigh: Color = DarkColorPaletteTokens.surfaceElevationHigh,
    onSurfaceElevationHigh: Color = DarkColorPaletteTokens.onSurfaceElevationHigh,
    error: Color = DarkColorPaletteTokens.error,
    onError: Color = DarkColorPaletteTokens.onError,
    outline: Color = DarkColorPaletteTokens.outline
) = ColorPalette(
    baseHue = baseHue,
    base = base,
    onBase = onBase,
    accentHue = accentHue,
    accent = accent,
    onAccent = onAccent,
    surfaceElevationLow = surfaceElevationLow,
    onSurfaceElevationLow = onSurfaceElevationLow,
    surfaceElevationMedium = surfaceElevationMedium,
    onSurfaceElevationMedium = onSurfaceElevationMedium,
    surfaceElevationHigh = surfaceElevationHigh,
    onSurfaceElevationHigh = onSurfaceElevationHigh,
    error = error,
    onError = onError,
    outline = outline
)

/**
 * A CompositionLocal key used for providing a [ColorPalette] to the UI.
 */
val LocalColorPalette = compositionLocalOf { lightColorPalette() }

/**
 * A CompositionLocal key used for providing a content [Color] to the UI.
 */
val LocalContentColor = compositionLocalOf { Color.Unspecified }