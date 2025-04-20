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

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

@Immutable
internal object DefaultTypefaceTokens : TypefaceTokens {

    override val titleLarge: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.1.sp
        )

    override val titleMedium: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.2.sp
        )

    override val titleSmall: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.2.sp
        )

    override val actionLarge: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.014).sp
        )

    override val actionMedium: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = (-0.12).sp
        )

    override val actionSmall: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = (-0.1).sp
        )

    override val bodyLarge: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 18.sp,
            letterSpacing = (-0.1).sp
        )

    override val bodyMedium: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.sp
        )

    override val bodySmall: TextStyle
        get() = DefaultTextStyle.copy(
            fontFamily = DefaultFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.sp
        )

}

@Immutable
internal interface TypefaceTokens {
    val titleLarge: TextStyle
    val titleMedium: TextStyle
    val titleSmall: TextStyle
    val actionLarge: TextStyle
    val actionMedium: TextStyle
    val actionSmall: TextStyle
    val bodyLarge: TextStyle
    val bodyMedium: TextStyle
    val bodySmall: TextStyle
}


internal val DefaultLineHeightStyle
    get() = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.None
    )

internal val DefaultTextStyle
    get() = TextStyle.Default.copy(
        platformStyle = null,
        lineHeightStyle = DefaultLineHeightStyle,
    )

internal val DefaultFontFamily
    get() = FontFamily.SansSerif