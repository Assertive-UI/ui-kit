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

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

/**
 * A collection of theme modes made for easier UI theme implementation.
 * ```
 * val themeMode = ThemeMode.Auto
 *
 * UITheme(themeMode = themeMode) {
 *     // ...
 * }
 * ```
 */
enum class ThemeMode {

    /**
     * A constant that represents an Automatic (System) theme mode.
     */
    System,

    /**
     * A constant that represents a Light theme mode.
     */
    Light,

    /**
     * A constant that represents a Dark theme mode.
     */
    Dark;

}

/**
 * A CompositionLocal key used for providing [ThemeMode] to the UI.
 */
val LocalThemeMode = compositionLocalOf { ThemeMode.System }

/**
 *
 * A function to check whether the currently applied [ThemeMode] is dark or not.
 *
 * @param themeMode An currently applied [ThemeMode].
 *
 */
@Composable
fun isInDarkThemeMode(
    themeMode: ThemeMode = LocalThemeMode.current
) = when (themeMode) {
    ThemeMode.System -> isSystemInDarkTheme()
    ThemeMode.Light -> false
    ThemeMode.Dark -> true
}