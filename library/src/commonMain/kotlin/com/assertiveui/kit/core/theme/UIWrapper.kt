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

package com.assertiveui.kit.core.theme

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.unit.DpSize
import com.assertiveui.kit.core.theme.color.palette.ColorPalette
import com.assertiveui.kit.core.theme.color.palette.LocalColorPalette
import com.assertiveui.kit.core.theme.color.palette.LocalContentColor
import com.assertiveui.kit.core.theme.color.palette.asAnimatedColorPalette
import com.assertiveui.kit.core.theme.color.palette.darkColorPalette
import com.assertiveui.kit.core.theme.color.palette.lightColorPalette
import com.assertiveui.kit.core.theme.shape.LocalShapes
import com.assertiveui.kit.core.theme.shape.Shapes
import com.assertiveui.kit.core.theme.typeface.LocalTextStyle
import com.assertiveui.kit.core.theme.typeface.LocalTypefaces
import com.assertiveui.kit.core.theme.typeface.Typefaces
import com.assertiveui.kit.core.utils.LocalWindowState
import com.assertiveui.kit.core.utils.rememberWindowState

/**
 *
 * An [UIWrapper] is at the core foundation of an application built with the Assertive UI Kit.
 * It provides essential configuration to Assertive UI Kit Components and much more.
 * Therefore, it's considered as a Top-level composable function.
 *
 * Make sure to wrap your application UI content inside an [UIWrapper].
 *
 * ```
 * @Composable
 * fun ApplicationUIWrapper(
 *     themeMode: ThemeMode = ThemeMode.Auto,
 *     colorPalette: ColorPalette = getDefaultColorPalette(isInDarkThemeMode(themeMode)),
 *     content: @Composable () -> Unit
 * ) {
 *
 *     UIWrapper(
 *         themeMode = themeMode,
 *         colorPalette = colorPalette,
 *         animateColorPalette = true,
 *         typefaces = EudoxusTypefaces(),
 *         shapes = Shapes.Default,
 *         content = content
 *     )
 *
 * }
 * ```
 *
 * ```
 * @Composable
 * fun App() {
 *
 *     ApplicationUIWrapper {
 *
 *         // UI Content ...
 *
 *     }
 *
 * }
 * ```
 *
 * @param themeMode The [ThemeMode] to be applied.
 * @param colorPalette The [ColorPalette] to be applied.
 * @param animateColorPalette Whether to animate the [ColorPalette] or not (e.g. when switching [ThemeMode]).
 * @param typefaces The [Typefaces] to be applied.
 * @param shapes The [Shapes] to be applied.
 * @param content The UI Content tree.
 *
 */
@Composable
fun UIWrapper(
    themeMode: ThemeMode = ThemeMode.System,
    colorPalette: ColorPalette = getDefaultColorPalette(isInDarkThemeMode(themeMode)),
    animateColorPalette: Boolean = true,
    typefaces: Typefaces = Typefaces.Default,
    shapes: Shapes = Shapes.Default,
    content: @Composable () -> Unit
) = BoxWithConstraints {

    val finalColorPalette by rememberUpdatedState(
        if (animateColorPalette) colorPalette.asAnimatedColorPalette()
        else colorPalette
    )

    val windowState by rememberWindowState(
        rootSize = DpSize(
            width = maxWidth,
            height = maxHeight
        )
    )

    CompositionLocalProvider(
        LocalWindowState provides windowState,
        LocalThemeMode provides themeMode,
        LocalColorPalette provides finalColorPalette,
        LocalContentColor provides finalColorPalette.onSurfaceElevationLow,
        LocalTypefaces provides typefaces,
        LocalTextStyle provides typefaces.bodyMedium,
        LocalShapes provides shapes,
        content = content
    )

}

/**
 *
 * Returns the default [ColorPalette] based on the current theme mode.
 *
 * @param darkTheme The the current theme is in dark mode.
 *
 */
fun getDefaultColorPalette(darkTheme: Boolean): ColorPalette {
    return (if (darkTheme) darkColorPalette()
    else lightColorPalette())
}