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

package com.assertiveui.kit.core.components.siderail.navigation.item

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.components.siderail.navigation.SideNavRailState
import com.assertiveui.kit.core.theme.Theme

/**
 * Utility object for handling the styling, animations, and interactions
 * of individual side navigation rail items in a custom Side Navigation component.
 *
 * Provides convenient [Modifier] extensions and utilities to enhance
 * the user interaction experience (press, focus, hover, selection) with visual feedback.
 */
object SideNavRailItemUtils {

    /**
     * Modifier extension for styling the container of a Side Navigation Rail item.
     *
     * Applies size, clickable behavior, and draws a radial gradient circle as visual feedback
     * based on hover, focus, and press states.
     *
     * @param index The index of the navigation item.
     * @param interactionSource The [MutableInteractionSource] tracking user interactions.
     * @param onClick Lambda to be invoked when the item is clicked, providing the [index].
     * @return The updated [Modifier].
     */
    internal fun Modifier.sideNavRailItemContainerModifier(
        index: Int,
        interactionSource: MutableInteractionSource,
        onClick: (index: Int) -> Unit
    ): Modifier {
        return composed {

            val isHovered by interactionSource.collectIsHoveredAsState()
            val isFocused by interactionSource.collectIsFocusedAsState()
            val isPressed by interactionSource.collectIsPressedAsState()

            val interactionHoloColor by animateColorAsState(
                targetValue = Theme.colorPalette.base.copy(
                    when {
                        isPressed -> .2f
                        isHovered || isFocused -> .1f
                        else -> 0f
                    }
                ),
                animationSpec = spring()
            )

            Modifier
                .size(
                    width = 64.dp,
                    height = 64.dp
                )
                .clickable(
                    onClick = { onClick(index) },
                    interactionSource = interactionSource,
                    indication = null
                )
                .drawBehind {

                    val radius = this.size.minDimension * .4f
                    val gradient = Brush.radialGradient(
                        colors = listOf(
                            interactionHoloColor,
                            interactionHoloColor.copy(0f)
                        ),
                        radius = radius,
                        center = this.center
                    )

                    drawCircle(
                        brush = gradient,
                        center = this.center,
                        radius = radius
                    )

                }

        }
    }

    /**
     * Modifier extension for styling the icon inside a Side Navigation Rail item.
     *
     * Applies animated scaling and horizontal offset effects based on interaction states
     * (pressed, hovered, focused) and selection state.
     *
     * @param index The index of the navigation item.
     * @param state The [SideNavRailState] representing the overall navigation state.
     * @param interactionSource The [MutableInteractionSource] tracking user interactions.
     * @return The updated [Modifier].
     */
    internal fun Modifier.sideNavRailItemIconModifier(
        index: Int,
        state: SideNavRailState,
        interactionSource: MutableInteractionSource
    ): Modifier {
        return composed {

            val density = LocalDensity.current
            val isLtr by rememberUpdatedState(
                LocalLayoutDirection.current == LayoutDirection.Ltr
            )

            val isHovered by interactionSource.collectIsHoveredAsState()
            val isFocused by interactionSource.collectIsFocusedAsState()
            val isPressed by interactionSource.collectIsPressedAsState()

            val iconOffsetYSpec by remember(state, index, isLtr) {
                derivedStateOf {
                    if (state.selectedItemIndex == index) {
                        (if (isLtr) 2.dp else (-2).dp)
                    } else 0.dp
                }
            }

            val iconOffsetY by animateDpAsState(
                targetValue = iconOffsetYSpec,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            val iconScale by animateFloatAsState(
                targetValue = when {
                    isPressed -> .9f
                    isHovered || isFocused -> 1.1f
                    else -> 1f
                },
                animationSpec = spring()
            )

            Modifier
                .graphicsLayer {
                    this.translationX += with(density) { iconOffsetY.toPx() }
                    this.scaleX = iconScale
                    this.scaleY = iconScale
                }
                .size(24.dp)

        }
    }

    /**
     * Composable function that determines the icon color for a Side Navigation Rail item.
     *
     * Animates the icon color based on whether the item is selected.
     *
     * @param selectedItemIndex The index of the currently selected navigation item.
     * @param index The index of the navigation item being rendered.
     * @return The animated [Color] to be used for the icon.
     */
    @Composable
    internal fun iconColor(
        selectedItemIndex: Int,
        index: Int
    ): Color = animateColorAsState(
        targetValue = if (selectedItemIndex == index) {
            Theme.colorPalette.base
        } else {
            Theme.colorPalette.onSurfaceElevationLow
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    ).value

}