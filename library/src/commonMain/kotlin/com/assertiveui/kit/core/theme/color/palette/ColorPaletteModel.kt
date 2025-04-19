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
import androidx.compose.ui.graphics.Color

/**
 *
 * A data class that contains a [ColorPalette] and the color tones used for generating the palette.
 *
 * @param lightColorPalette The generated light [ColorPalette].
 * @param darkColorPalette The generated dark [ColorPalette].
 * @param baseColorTonesHue The hue of the base color tones.
 * @param baseColorTones The base color tones.
 * @param accentColorTonesHue The hue of the accent color tones.
 * @param accentColorTones The accent color tones.
 * @param surfaceColorTones The surface color tones.
 * @param errorColorTones The error color tones.
 *
 */
@Stable
data class ColorPaletteModel(
    val lightColorPalette: ColorPalette = lightColorPalette(),
    val darkColorPalette: ColorPalette = darkColorPalette(),
    val baseColorTonesHue: Int = LightColorPaletteTokens.baseHue,
    val baseColorTones: List<Color> = emptyList(),
    val accentColorTonesHue: Int = LightColorPaletteTokens.accentHue,
    val accentColorTones: List<Color> = emptyList(),
    val surfaceColorTones: List<Color> = emptyList(),
    val errorColorTones: List<Color> = emptyList(),
)