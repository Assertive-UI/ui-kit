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

package com.assertiveui.kit.core.theme.typeface

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import assertiveuikit.library.generated.resources.Res
import assertiveuikit.library.generated.resources.eudoxus_sans_bold
import assertiveuikit.library.generated.resources.eudoxus_sans_extra_bold
import assertiveuikit.library.generated.resources.eudoxus_sans_extra_light
import assertiveuikit.library.generated.resources.eudoxus_sans_light
import assertiveuikit.library.generated.resources.eudoxus_sans_medium
import assertiveuikit.library.generated.resources.eudoxus_sans_regular
import org.jetbrains.compose.resources.Font

@Composable
fun EudoxusFontFamily() = FontFamily(
    Font(Res.font.eudoxus_sans_extra_light, weight = FontWeight.ExtraLight),
    Font(Res.font.eudoxus_sans_light, weight = FontWeight.Light),
    Font(Res.font.eudoxus_sans_regular, weight = FontWeight.Normal),
    Font(Res.font.eudoxus_sans_medium, weight = FontWeight.Medium),
    Font(Res.font.eudoxus_sans_bold, weight = FontWeight.Bold),
    Font(Res.font.eudoxus_sans_extra_bold, weight = FontWeight.ExtraBold),
)

@Composable
fun EudoxusTypefaces() = Typefaces().run {
    val fontFamily = EudoxusFontFamily()
    copy(
        titleLarge = titleLarge.copy(fontFamily = fontFamily),
        titleMedium = titleMedium.copy(fontFamily = fontFamily),
        titleSmall = titleSmall.copy(fontFamily = fontFamily),
        actionLarge = actionLarge.copy(fontFamily = fontFamily),
        actionMedium = actionMedium.copy(fontFamily = fontFamily),
        actionSmall = actionSmall.copy(fontFamily = fontFamily),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = bodySmall.copy(fontFamily = fontFamily),
    )
}