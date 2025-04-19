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
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

/**
 *
 * A collection of [TextStyle]s that can be applied to the application theme.
 *
 * @param titleLarge A [TextStyle] intended for a title that contains a short text.
 * @param titleMedium A [TextStyle] intended for a title that contains a medium text.
 * @param titleSmall A [TextStyle] intended for a title that contains a long text.
 * @param actionLarge A [TextStyle] intended for an action that contains a short text.
 * @param actionMedium A [TextStyle] intended for an action that contains a medium text.
 * @param actionSmall A [TextStyle] intended for an action that contains a long text.
 * @param bodyLarge A [TextStyle] intended for a short body text.
 * @param bodyMedium A [TextStyle] intended for a medium body text.
 * @param bodySmall A [TextStyle] intended for a long body text.
 *
 */
@Immutable
class Typefaces(
    val titleLarge: TextStyle = DefaultTypefaceTokens.titleLarge,
    val titleMedium: TextStyle = DefaultTypefaceTokens.titleMedium,
    val titleSmall: TextStyle = DefaultTypefaceTokens.titleSmall,
    val actionLarge: TextStyle = DefaultTypefaceTokens.actionLarge,
    val actionMedium: TextStyle = DefaultTypefaceTokens.actionMedium,
    val actionSmall: TextStyle = DefaultTypefaceTokens.actionSmall,
    val bodyLarge: TextStyle = DefaultTypefaceTokens.bodyLarge,
    val bodyMedium: TextStyle = DefaultTypefaceTokens.bodyMedium,
    val bodySmall: TextStyle = DefaultTypefaceTokens.bodySmall,
) {

    companion object {

        /**
         * Returns the default [Typefaces].
         */
        val Default get() = Typefaces()

    }

    /**
     * Creates a copy of this [Typefaces], with an optional overriding.
     */
    fun copy(
        titleLarge: TextStyle = this.titleLarge,
        titleMedium: TextStyle = this.titleMedium,
        titleSmall: TextStyle = this.titleSmall,
        actionLarge: TextStyle = this.actionLarge,
        actionMedium: TextStyle = this.actionMedium,
        actionSmall: TextStyle = this.actionSmall,
        bodyLarge: TextStyle = this.bodyLarge,
        bodyMedium: TextStyle = this.bodyMedium,
        bodySmall: TextStyle = this.bodySmall
    ) = Typefaces(
        titleLarge = titleLarge,
        titleMedium = titleMedium,
        titleSmall = titleSmall,
        actionLarge = actionLarge,
        actionMedium = actionMedium,
        actionSmall = actionSmall,
        bodyLarge = bodyLarge,
        bodyMedium = bodyMedium,
        bodySmall = bodySmall
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Typefaces) return false
        if (titleLarge != other.titleLarge) return false
        if (titleMedium != other.titleMedium) return false
        if (titleSmall != other.titleSmall) return false
        if (actionLarge != other.actionLarge) return false
        if (actionMedium != other.actionMedium) return false
        if (actionSmall != other.actionSmall) return false
        if (bodyLarge != other.bodyLarge) return false
        if (bodyMedium != other.bodyMedium) return false
        if (bodySmall != other.bodySmall) return false
        return true
    }

    override fun hashCode(): Int {
        var result = titleLarge.hashCode()
        result += titleMedium.hashCode()
        result += titleSmall.hashCode()
        result += actionLarge.hashCode()
        result += actionMedium.hashCode()
        result += actionSmall.hashCode()
        result += bodyLarge.hashCode()
        result += bodyMedium.hashCode()
        result += bodySmall.hashCode()
        return result
    }

    override fun toString(): String {
        return "Typefaces(" +
                "titleLarge=$titleLarge," +
                "titleMedium=$titleMedium, " +
                "titleSmall=$titleSmall, " +
                "actionLarge=$actionLarge, " +
                "actionMedium=$actionMedium, " +
                "actionSmall=$actionSmall, " +
                "bodyLarge=$bodyLarge, " +
                "bodyMedium=$bodyMedium, " +
                "bodySmall=$bodySmall" +
                ")"
    }

}

/**
 * A CompositionLocal key used for providing [Typefaces] to the UI.
 */
val LocalTypefaces = staticCompositionLocalOf { Typefaces.Default }

/**
 * A CompositionLocal key used for providing a default [TextStyle] to the UI.
 */
val LocalTextStyle = compositionLocalOf { DefaultTypefaceTokens.bodyMedium }