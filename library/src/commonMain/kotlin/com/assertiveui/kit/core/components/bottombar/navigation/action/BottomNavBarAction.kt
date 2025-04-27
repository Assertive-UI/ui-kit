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

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.assertiveui.kit.core.components.bottombar.navigation.action.BottomNavBarActionUtils.bottomNavBarActionClickableContainerModifier
import com.assertiveui.kit.core.components.bottombar.navigation.action.BottomNavBarActionUtils.bottomNavBarActionContainerModifier
import com.assertiveui.kit.core.components.bottombar.navigation.action.BottomNavBarActionUtils.bottomNavBarActionIconModifier

/**
 * Represents an independent action within the bottom navigation bar,
 * typically placed alongside navigation items to perform a primary action (e.g., opening a menu).
 *
 * This composable handles user interaction states like hover, focus, and press,
 * applying animated feedback effects such as padding changes and background color transitions.
 *
 * @param icon The [Painter] used to display the action's icon.
 * @param onClick Callback triggered when the action is clicked.
 * @param contentDescription A description of the action icon for accessibility support.
 */
@Composable
fun BottomNavBarAction(
    icon: Painter,
    onClick: () -> Unit,
    contentDescription: String?
) {

    Box(
        modifier = Modifier.bottomNavBarActionContainerModifier(),
        contentAlignment = Alignment.Center,
        content = {

            Box(
                modifier = Modifier.bottomNavBarActionClickableContainerModifier(onClick),
                contentAlignment = Alignment.Center,
                content = {

                    Icon(
                        modifier = Modifier.bottomNavBarActionIconModifier(),
                        painter = icon,
                        contentDescription = contentDescription,
                        tint = BottomNavBarActionUtils.iconColor
                    )

                }
            )

        }
    )

}