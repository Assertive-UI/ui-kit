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

package com.assertiveui.kit.lite.core.theme.color.utils

import androidx.compose.ui.graphics.Color
import com.assertiveui.kit.lite.core.theme.color.palette.ColorPalette
import com.assertiveui.kit.lite.core.theme.color.palette.ColorPaletteModel
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtils.generateColorTones
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorPaletteGeneration.ACCENT_HUE_OFFSET
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorPaletteGeneration.DarkColorsExtraction
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorPaletteGeneration.LightColorsExtraction
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_ALPHA
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_COUNT
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_HUE_OFFSET
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_LIGHTNESS_END
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_LIGHTNESS_MID
import com.assertiveui.kit.lite.core.theme.color.utils.ColorUtilsTokens.ColorToneGeneration.TONES_LIGHTNESS_START
import com.assertiveui.kit.lite.core.theme.color.utils.HueOffsetDirection.DOWN
import com.assertiveui.kit.lite.core.theme.color.utils.HueOffsetDirection.UP
import com.github.ajalt.colormath.extensions.android.composecolor.toComposeColor
import com.github.ajalt.colormath.model.LCHab
import com.github.ajalt.colormath.model.Oklab
import com.github.ajalt.colormath.transform.InterpolationMethods
import com.github.ajalt.colormath.transform.interpolator
import com.github.ajalt.colormath.transform.sequence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 *
 * A collection of crafted Utilities,
 * ranging from generating color tones, to more advanced color science.
 *
 * @property generateColorTones A function used for generating uniform color shades.
 *
 */
object ColorUtils {

    /**
     *
     * A function used to generate a [ColorPaletteModel]
     * based on a hue value, hue offset, hue offset direction and theme condition.
     *
     * @param baseHue The base hue of the palette.
     * @param accentHueOffset The hue offset for the accent color tones hue of the palette.
     * @param accentHueOffsetDirection The offset direction for the accent color tones  hue of the palette.
     *
     */
    suspend fun generateColorPaletteModel(
        baseHue: Int,
        accentHueOffset: Int = ACCENT_HUE_OFFSET,
        accentHueOffsetDirection: HueOffsetDirection = DOWN
    ): ColorPaletteModel {
        return withContext(Dispatchers.Default) {

            val baseTones = generateColorTones(hue = baseHue)

            val accentTonesHue = calculateAccentHue(
                baseHue = baseHue,
                hueOffset = accentHueOffset,
                hueOffsetDirection = accentHueOffsetDirection
            )

            val accentTones = generateColorTones(hue = accentTonesHue)

            val surfaceTones = generateColorTones(
                hue = baseHue,
                chroma = ColorToneChroma.MUTED
            )

            val errorTonesHue = 0
            val errorTones = generateColorTones(hue = errorTonesHue)

            val lightColorPalette = ColorPalette(
                baseHue = baseHue,
                base = baseTones[LightColorsExtraction.BASE],
                onBase = baseTones[LightColorsExtraction.ON_BASE],
                accentHue = accentTonesHue,
                accent = accentTones[LightColorsExtraction.ACCENT],
                onAccent = accentTones[LightColorsExtraction.ON_ACCENT],
                surfaceLow = surfaceTones[LightColorsExtraction.SURFACE_LOW],
                onSurfaceLow = surfaceTones[LightColorsExtraction.ON_SURFACE_LOW],
                surfaceMedium = surfaceTones[LightColorsExtraction.SURFACE_MEDIUM],
                onSurfaceMedium = surfaceTones[LightColorsExtraction.ON_SURFACE_MEDIUM],
                surfaceHigh = surfaceTones[LightColorsExtraction.SURFACE_HIGH],
                onSurfaceHigh = surfaceTones[LightColorsExtraction.ON_SURFACE_HIGH],
                error = errorTones[LightColorsExtraction.ERROR],
                onError = errorTones[LightColorsExtraction.ON_ERROR],
                outline = surfaceTones[LightColorsExtraction.OUTLINE]
            )

            val darkColorPalette = ColorPalette(
                baseHue = baseHue,
                base = baseTones[DarkColorsExtraction.BASE],
                onBase = baseTones[DarkColorsExtraction.ON_BASE],
                accentHue = accentTonesHue,
                accent = accentTones[DarkColorsExtraction.ACCENT],
                onAccent = accentTones[DarkColorsExtraction.ON_ACCENT],
                surfaceLow = surfaceTones[DarkColorsExtraction.SURFACE_LOW],
                onSurfaceLow = surfaceTones[DarkColorsExtraction.ON_SURFACE_LOW],
                surfaceMedium = surfaceTones[DarkColorsExtraction.SURFACE_MEDIUM],
                onSurfaceMedium = surfaceTones[DarkColorsExtraction.ON_SURFACE_MEDIUM],
                surfaceHigh = surfaceTones[DarkColorsExtraction.SURFACE_HIGH],
                onSurfaceHigh = surfaceTones[DarkColorsExtraction.ON_SURFACE_HIGH],
                error = errorTones[DarkColorsExtraction.ERROR],
                onError = errorTones[DarkColorsExtraction.ON_ERROR],
                outline = surfaceTones[DarkColorsExtraction.OUTLINE]
            )

            val colorPaletteModel = ColorPaletteModel(
                lightColorPalette = lightColorPalette,
                darkColorPalette = darkColorPalette,
                baseColorTonesHue = baseHue,
                baseColorTones = baseTones,
                accentColorTonesHue = accentTonesHue,
                accentColorTones = accentTones,
                surfaceColorTones = surfaceTones,
                errorColorTones = errorTones
            )

            return@withContext colorPaletteModel

        }
    }

    /**
     *
     * A helper function used to calculate an accent hue by using a base hue and offset.
     *
     * @param baseHue The base hue.
     * @param hueOffset The hue offset.
     *
     */
    private fun calculateAccentHue(
        baseHue: Int,
        hueOffset: Int,
        hueOffsetDirection: HueOffsetDirection
    ): Int = when (hueOffsetDirection) {
        DOWN -> baseHue - hueOffset
        UP -> baseHue + hueOffset
    }

    /**
     *
     * A function used to generate uniform color tones derived from a single hue value
     * by using an [Oklab] color space interpolation from the [com.github.ajalt.colormath]
     * library and producing variety of color tones.
     *
     * The interpolator use Monotonic spline
     * interpolation that interpolates smoothly between each pair of input points.
     *
     * Make sure to call this function in a thread-safe manner as it's performing
     * heavy calculations under-the-hood.
     *
     * @param hue The hue value ranging from 0 to 359.
     * @param tones The number of generated tones.
     * @param chroma Whether to generate a vibrant, muted tones or tones with zero chroma.
     *
     */
    private suspend fun generateColorTones(
        hue: Int,
        tones: Int = TONES_COUNT,
        chroma: ColorToneChroma = ColorToneChroma.VIBRANT
    ): List<Color> {
        return withContext(Dispatchers.Default) {

            val interpolator = Oklab.interpolator {

                method = InterpolationMethods.monotoneSpline()

                stop(
                    color = LCHab(
                        l = TONES_LIGHTNESS_START,
                        c = ColorToneChroma.ZERO.value,
                        h = hue + TONES_HUE_OFFSET,
                        alpha = TONES_ALPHA
                    )
                )

                stop(
                    color = LCHab(
                        l = TONES_LIGHTNESS_MID,
                        c = chroma.value,
                        h = hue + TONES_HUE_OFFSET,
                        alpha = TONES_ALPHA
                    )
                )

                stop(
                    color = LCHab(
                        l = TONES_LIGHTNESS_END,
                        c = ColorToneChroma.ZERO.value,
                        h = hue + TONES_HUE_OFFSET,
                        alpha = TONES_ALPHA
                    )
                )

            }

            return@withContext interpolator
                .sequence(tones)
                .distinct()
                .toList()
                .map { it.toComposeColor() }

        }
    }

}
