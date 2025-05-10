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

package com.assertiveui.kit.core.components.siderail.navigation

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.components.bottombar.navigation.BottomNavBarState
import com.assertiveui.kit.core.theme.Theme

/**
 * Utility object for handling common configurations related to the Side Navigation Rail.
 */
object SideNavRailUtils {

    /**
     * Modifier that defines the appearance and padding of the side navigation rail container.
     *
     * Applies window insets, internal padding, fixed height, shape clipping, background color,
     * and a subtle border.
     *
     * @param windowInsets The [WindowInsets] to apply padding for system bars.
     */
    internal fun Modifier.sideNavRailContainerModifier(
        windowInsets: WindowInsets
    ): Modifier {
        return composed {
            Modifier
                .windowInsetsPadding(windowInsets)
                .padding(vertical = 32.dp)
                .padding(start = 16.dp)
                .width(containerWidth)
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
     * Provides [WindowInsets] that only account for start and vertical system bars.
     *
     * This ensures that the side navigation rail adjusts properly
     * to system UI elements like the navigation gestures or system bars.
     *
     * @return A [WindowInsets] instance reflecting both start and vertical insets.
     */
    @Composable
    fun windowInsets(): WindowInsets {
        return rememberUpdatedState(
            WindowInsets.safeContent.only(
                sides = WindowInsetsSides.Vertical + WindowInsetsSides.Start
            )
        ).value
    }

    /**
     * The standard width for a side navigation rail container.
     *
     * @return A [Dp] value representing the container width (64.dp).
     */
    private val containerWidth: Dp get() = 64.dp

    /**
     * Calculates and remembers the dynamic height of the side navigation rail container
     * based on the number of navigation items.
     *
     * @param state The [BottomNavBarState] containing the item count.
     * @return A [State] containing the computed height as [Dp].
     */
    @Composable
    internal fun containerHeight(state: SideNavRailState): State<Dp> {
        return rememberUpdatedState(48.dp + (64.dp * state.itemsCount))
    }

    /**
     * Modifier that defines the visual indicator
     * for the currently selected side navigation rail item.
     *
     * Animates its height and vertical offset based on the selected item index.
     *
     * @param layoutWidth The total width of the side navigation layout.
     * @param layoutHeight The total height of the side navigation layout.
     * @param state The [SideNavRailState] used to determine the selected item.
     */
    internal fun Modifier.sideNavRailSelectedItemIndicatorModifier(
        layoutWidth: Int,
        layoutHeight: Dp,
        state: SideNavRailState
    ): Modifier {
        return composed {

            val density = LocalDensity.current
            val layoutDirection = LocalLayoutDirection.current

            val height by animateDpAsState(
                targetValue = (layoutHeight - 48.dp) / (state.itemsCount * 2)
            )

            val offsetYSpec by remember(layoutWidth, state, layoutDirection) {
                derivedStateOf {
                    if (layoutDirection == LayoutDirection.Ltr) {
                        28.dp + ((layoutHeight - 48.dp) / state.itemsCount) *
                                state.selectedItemIndex.coerceIn(0, state.itemsCount)
                    } else {
                        (layoutHeight - (height - 12.dp)) -
                                ((layoutHeight - 48.dp) / state.itemsCount) *
                                (state.itemsCount - state.selectedItemIndex
                                    .coerceIn(0, state.itemsCount))
                    }
                }
            }

            val offsetX by remember(
                density,
                layoutDirection,
                height
            ) {
                derivedStateOf {
                    with(density) {
                        if (layoutDirection == LayoutDirection.Ltr) {
                            8.dp.roundToPx() - height.roundToPx()
                        } else (-height.roundToPx() / 2) - 8.dp.roundToPx()
                    }
                }
            }

            val offsetY by animateDpAsState(
                targetValue = offsetYSpec,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )

            val intOffset by rememberUpdatedState(
                IntOffset(
                    x = offsetX,
                    y = with(density) { offsetY.roundToPx() }
                )
            )

            Modifier
                .offset { intOffset }
                .size(height)
                .background(
                    color = Theme.colorPalette.base,
                    shape = CircleShape
                )

        }
    }

    /**
     * Modifier that defines the layout behavior of individual side navigation rail items.
     *
     * Ensures items fill the available width and have consistent vertical spacing.
     */
    internal fun Modifier.sideNavRailItemContainerModifier(): Modifier {
        return fillMaxWidth()
            .padding(vertical = 12.dp)
    }

}