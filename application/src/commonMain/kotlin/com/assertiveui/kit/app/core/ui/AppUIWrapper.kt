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

package com.assertiveui.kit.app.core.ui

import androidx.compose.runtime.Composable
import com.assertiveui.kit.core.theme.ThemeMode
import com.assertiveui.kit.core.theme.UIWrapper
import com.assertiveui.kit.core.theme.color.palette.ColorPalette
import com.assertiveui.kit.core.theme.getDefaultColorPalette
import com.assertiveui.kit.core.theme.isInDarkThemeMode
import com.assertiveui.kit.core.theme.shape.Shapes
import com.assertiveui.kit.core.theme.typeface.EudoxusTypefaces

@Composable
fun AppUIWrapper(
    themeMode: ThemeMode = ThemeMode.System,
    colorPalette: ColorPalette = getDefaultColorPalette(isInDarkThemeMode(themeMode)),
    content: @Composable () -> Unit
) {

    UIWrapper(
        themeMode = themeMode,
        colorPalette = colorPalette,
        animateColorPalette = false,
        typefaces = EudoxusTypefaces(),
        shapes = Shapes.Default,
        content = content
    )

}