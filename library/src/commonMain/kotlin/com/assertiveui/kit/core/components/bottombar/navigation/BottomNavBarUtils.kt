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

package com.assertiveui.kit.core.components.bottombar.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.theme.Theme

/**
 * Utility object for handling common configurations related to the Bottom Navigation Bar.
 */
object BottomNavBarUtils {

    /**
     * Modifier that defines the appearance and padding of the bottom navigation bar container.
     *
     * Applies window insets, internal padding, fixed height, shape clipping, background color,
     * and a subtle border.
     *
     * @param windowInsets The [WindowInsets] to apply padding for system bars.
     */
    internal fun Modifier.bottomNavBarContainerModifier(
        windowInsets: WindowInsets
    ): Modifier {
        return composed {
            Modifier
                .windowInsetsPadding(windowInsets)
                .padding(horizontal = 32.dp)
                .padding(bottom = 16.dp)
                .height(containerHeight)
                .clip(Theme.shapes.largeShape)
                .background(
                    color = Theme.colorPalette.surfaceElevationHigh,
                    shape = Theme.shapes.largeShape
                )
                .border(
                    width = 1.dp,
                    color = Theme.colorPalette.surfaceElevationLow.copy(.2f),
                    shape = Theme.shapes.largeShape
                )
        }
    }

    /**
     * Provides [WindowInsets] that only account for horizontal system bars and the bottom system bar.
     *
     * This ensures that the bottom navigation bar adjusts properly to system UI elements like the
     * navigation gestures or system bars, while ignoring top insets (e.g., status bar).
     *
     * @return A [WindowInsets] instance reflecting only horizontal and bottom insets.
     */
    @Composable
    fun windowInsets(): WindowInsets {
        return rememberUpdatedState(
            WindowInsets.systemBars.only(
                sides = WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
            )
        ).value
    }

    /**
     * Calculates and remembers the dynamic width of the bottom navigation bar container
     * based on the number of navigation items.
     *
     * @param state The [BottomNavBarState] containing the item count.
     * @return A [State] containing the computed width as [Dp].
     */
    @Composable
    internal fun containerWidth(state: BottomNavBarState): State<Dp> {
        return rememberUpdatedState(48.dp + (64.dp * state.itemsCount))
    }

    /**
     * The standard height for a bottom navigation bar container.
     *
     * @return A [Dp] value representing the container height (64.dp).
     */
    private val containerHeight: Dp get() = 64.dp

    /**
     * Modifier that defines the visual indicator for the currently selected bottom navigation item.
     *
     * Animates its width and horizontal offset based on the selected item index.
     *
     * @param layoutWidth The total width of the bottom navigation layout.
     * @param layoutHeight The total height of the bottom navigation layout.
     * @param state The [BottomNavBarState] used to determine the selected item.
     */
    internal fun Modifier.bottomNavBarSelectedItemIndicatorModifier(
        layoutWidth: Dp,
        layoutHeight: Int,
        state: BottomNavBarState
    ): Modifier {
        return composed {

            val isLtr by rememberUpdatedState(
                LocalLayoutDirection.current == LayoutDirection.Ltr
            )

            val width by animateDpAsState(
                targetValue = (layoutWidth - 48.dp) / (state.itemsCount * 2)
            )

            val offsetXSpec by remember(layoutWidth, state, isLtr) {
                derivedStateOf {
                    if (isLtr) {
                        28.dp + ((layoutWidth - 48.dp) / state.itemsCount) *
                                state.selectedItemIndex.coerceIn(0, state.itemsCount)
                    } else {
                        (layoutWidth - (width - 12.dp)) - ((layoutWidth - 48.dp) / state.itemsCount) *
                                (state.itemsCount - state.selectedItemIndex
                                    .coerceIn(0, state.itemsCount))
                    }
                }
            }

            val offsetX by animateDpAsState(
                targetValue = offsetXSpec,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            Modifier
                .offset {
                    IntOffset(
                        x = offsetX.roundToPx(),
                        y = layoutHeight - 8.dp.roundToPx()
                    )
                }
                .size(width)
                .background(
                    color = Theme.colorPalette.base,
                    shape = CircleShape
                )

        }
    }

    /**
     * Modifier that defines the layout behavior of individual bottom navigation bar items.
     *
     * Ensures items fill the available height and have consistent horizontal spacing.
     */
    internal fun Modifier.bottomNavBarItemContainerModifier(): Modifier {
        return fillMaxHeight()
            .padding(horizontal = 12.dp)
    }

}