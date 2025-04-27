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

package com.assertiveui.kit.core.components.bottombar.navigation.item

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.assertiveui.kit.core.components.bottombar.navigation.BottomNavBarState
import com.assertiveui.kit.core.components.bottombar.navigation.item.BottomNavBarItemUtils.bottomNavBarItemContainerModifier
import com.assertiveui.kit.core.components.bottombar.navigation.item.BottomNavBarItemUtils.bottomNavBarItemIconModifier

/**
 * Represents an individual item within a bottom navigation bar.
 *
 * This composable handles user interactions like hover, focus, and press states,
 * applies animations to the icon, and updates its appearance based on whether it
 * is the selected item.
 *
 * @param index The index of this item in the bottom navigation bar.
 * @param icon The [Painter] resource to be drawn as the icon.
 * @param contentDescription A description of the icon for accessibility purposes.
 * @param state The current [BottomNavBarState] containing the selected item information.
 * @param onClick Callback triggered when this item is clicked, providing the clicked index.
 */
@Composable
fun BottomNavBarItem(
    index: Int,
    icon: Painter,
    contentDescription: String?,
    state: BottomNavBarState,
    onClick: (clickedIndex: Int) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .bottomNavBarItemContainerModifier(
                index = index,
                interactionSource = interactionSource,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center,
        content = {

            Icon(
                modifier = Modifier
                    .bottomNavBarItemIconModifier(
                        index = index,
                        state = state,
                        interactionSource = interactionSource
                    ),
                painter = icon,
                contentDescription = contentDescription,
                tint = BottomNavBarItemUtils.iconColor(
                    selectedItemIndex = state.selectedItemIndex,
                    index = index
                )
            )

        }
    )

}

/**
 * Defines a scope for creating multiple Bottom Navigation Bar items
 * inside a layout like a [Row].
 *
 * You can use this to easily build a list of bottom navigation items in a structured way.
 */
typealias BottomNavBarItemScope = @Composable RowScope.() -> Unit