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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import com.assertiveui.kit.core.theme.Theme.colorPalette
import com.assertiveui.kit.core.theme.Theme.shapes
import com.assertiveui.kit.core.theme.Theme.typefaces
import com.assertiveui.kit.core.theme.color.palette.ColorPalette
import com.assertiveui.kit.core.theme.color.palette.LocalColorPalette
import com.assertiveui.kit.core.theme.shape.LocalShapes
import com.assertiveui.kit.core.theme.shape.Shapes
import com.assertiveui.kit.core.theme.typeface.LocalTypefaces
import com.assertiveui.kit.core.theme.typeface.Typefaces

/**
 *
 * A simplified way to access the Assertive UI Kit theme CompositionLocal keys.
 *
 * @property colorPalette The currently applied [ColorPalette] to the theme.
 * @property typefaces The currently applied [Typefaces] to the theme.
 * @property shapes The currently applied [Shapes] to the theme.
 *
 */
@Stable
object Theme {

    /**
     * The currently applied [ColorPalette] to the theme.
     */
    val colorPalette: ColorPalette
        @ReadOnlyComposable
        @Composable
        get() = LocalColorPalette.current

    /**
     * The currently applied [Typefaces] to the theme.
     */
    val typefaces: Typefaces
        @ReadOnlyComposable
        @Composable
        get() = LocalTypefaces.current

    /**
     * The currently applied [Shapes] to the theme.
     */
    val shapes: Shapes
        @ReadOnlyComposable
        @Composable
        get() = LocalShapes.current

}