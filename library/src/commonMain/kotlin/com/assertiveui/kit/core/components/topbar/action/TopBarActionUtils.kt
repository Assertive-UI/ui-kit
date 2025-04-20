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

package com.assertiveui.kit.core.components.topbar.action

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.theme.Theme

object TopBarActionUtils {

    // Container

    internal fun Modifier.topBarActionContainerModifier(
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

            val borderColor by animateColorAsState(
                targetValue = when {
                    isPressed -> Theme.colorPalette.onSurfaceElevationLow.copy(.5f)
                    isHovered -> Theme.colorPalette.onSurfaceElevationLow.copy(.33f)
                    isFocused -> Theme.colorPalette.onSurfaceElevationLow.copy(.33f)
                    else -> Theme.colorPalette.onSurfaceElevationLow.copy(.16f)
                },
                animationSpec = spring()
            )

            Modifier
                .size(clickableContainerSize)
                .clickable(
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = null
                )
                .padding(contentPadding)
                .clip(clickableContainerShape)
                .background(
                    color = clickableContainerColor,
                    shape = clickableContainerShape
                )
                .border(
                    border = BorderStroke(
                        width = 1.dp,
                        color = borderColor
                    ),
                    shape = clickableContainerShape
                )

        }
    }

    private val clickableContainerSize: DpSize
        get() = DpSize(48.dp, 48.dp)

    private val clickableContainerShape: Shape
        @Composable
        @ReadOnlyComposable
        get() = Theme.shapes.smallShape

    private val clickableContainerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = Theme.colorPalette.surfaceElevationMedium

    // Icon

    internal fun Modifier.topBarActionIconModifier(): Modifier {
        return composed { Modifier.size(iconContainerSize) }
    }

    internal val iconColor: Color
        @Composable
        @ReadOnlyComposable
        get() = Theme.colorPalette.onSurfaceElevationMedium

    private val iconContainerSize: DpSize
        get() = DpSize(24.dp, 24.dp)

}