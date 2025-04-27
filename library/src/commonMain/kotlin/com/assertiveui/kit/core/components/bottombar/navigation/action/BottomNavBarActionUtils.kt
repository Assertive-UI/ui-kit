/*
 * Copyright 2025 Assertive UI (assertiveui.com)
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

package com.assertiveui.kit.core.components.bottombar.navigation.action

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.theme.Theme

/**
 * Utility object for handling the styling, sizing, and interactions
 * of action buttons inside a Bottom Navigation Bar.
 *
 * Provides [Modifier] extensions and styling values to maintain consistent
 * action button design and responsive interaction feedback.
 */
object BottomNavBarActionUtils {

    /**
     * Modifier extension that defines the fixed size of a Bottom Navigation Bar action container.
     *
     * @return The updated [Modifier] with a size of 64.dp x 64.dp.
     */
    internal fun Modifier.bottomNavBarActionContainerModifier(): Modifier {
        return size(64.dp)
    }

    /**
     * Modifier extension that applies clickable behavior, hover/focus/press interaction feedback,
     * animated content padding, and styled background to the Bottom Navigation Bar action container.
     *
     * @param onClick Lambda to be invoked when the action is clicked.
     * @return The updated [Modifier] with interactive behavior and styling.
     */
    internal fun Modifier.bottomNavBarActionClickableContainerModifier(
        onClick: () -> Unit
    ): Modifier {
        return composed {

            val interactionSource = remember { MutableInteractionSource() }
            val isHovered by interactionSource.collectIsHoveredAsState()
            val isFocused by interactionSource.collectIsHoveredAsState()
            val isPressed by interactionSource.collectIsPressedAsState()

            val contentPadding by animateDpAsState(
                targetValue = when {
                    isPressed -> 6.dp
                    isFocused -> 3.dp
                    isHovered -> 3.dp
                    else -> 4.dp
                },
                animationSpec = spring()
            )

            Modifier
                .size(clickableContainerSize)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                )
                .then(
                    Modifier
                        .padding(contentPadding)
                        .clip(clickableContainerShape)
                        .background(
                            color = clickableContainerColor,
                            shape = clickableContainerShape
                        )
                )

        }
    }

    /**
     * Predefined size for the clickable container of a Bottom Navigation Bar action.
     */
    private val clickableContainerSize: DpSize
        get() = DpSize(48.dp, 48.dp)

    /**
     * Predefined shape for the clickable container of a Bottom Navigation Bar action.
     *
     * This shape is based on the current [Theme]'s `smallShape`.
     */
    private val clickableContainerShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = Theme.shapes.smallShape

    /**
     * Predefined background color for the clickable container of a Bottom Navigation Bar action.
     *
     * This color is based on the current [Theme]'s `base` color.
     */
    private val clickableContainerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = Theme.colorPalette.base

    /**
     * Modifier extension that defines the fixed size for an icon inside a Bottom Navigation Bar action.
     *
     * @return The updated [Modifier] with a size of 24.dp x 24.dp.
     */
    internal fun Modifier.bottomNavBarActionIconModifier(): Modifier {
        return size(iconContainerSize)
    }

    /**
     * Predefined color for the icon inside a Bottom Navigation Bar action.
     *
     * This color is based on the current [Theme]'s `onBase` color.
     */
    internal val iconColor: Color
        @Composable
        @ReadOnlyComposable
        get() = Theme.colorPalette.onBase

    /**
     * Predefined size for an icon inside a Bottom Navigation Bar action.
     */
    private val iconContainerSize: DpSize
        get() = DpSize(24.dp, 24.dp)

}