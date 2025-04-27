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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.assertiveui.kit.core.components.bottombar.navigation.BottomNavBarUtils.bottomNavBarContainerModifier
import com.assertiveui.kit.core.components.bottombar.navigation.BottomNavBarUtils.bottomNavBarItemContainerModifier
import com.assertiveui.kit.core.components.bottombar.navigation.BottomNavBarUtils.bottomNavBarSelectedItemIndicatorModifier
import com.assertiveui.kit.core.components.bottombar.navigation.item.BottomNavBarItemScope

/**
 * A customizable Bottom Navigation Bar composable that arranges navigation items horizontally,
 * and displays a smooth animated indicator for the currently selected item.
 *
 * The navigation bar automatically handles system bar insets (e.g., gesture navigation padding)
 * and adjusts its layout based on the provided [BottomNavBarState].
 *
 * @param modifier Optional [Modifier] for additional customization outside the default layout behavior.
 * @param state The [BottomNavBarState] representing the current navigation state,
 *              including the selected item index and total item count.
 * @param items A [BottomNavBarItemScope] lambda used to define the navigation items displayed in the bar.
 * @param windowInsets Optional [WindowInsets] to apply system padding. Defaults to system bars (horizontal + bottom).
 */
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    state: BottomNavBarState,
    items: BottomNavBarItemScope,
    windowInsets: WindowInsets = BottomNavBarUtils.windowInsets()
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .bottomNavBarContainerModifier(windowInsets)
        ) {

            val layoutWidth by BottomNavBarUtils.containerWidth(state)
            val layoutHeight = constraints.maxHeight

            Row(
                modifier = Modifier.bottomNavBarItemContainerModifier(),
                content = items
            )

            Box(
                modifier = Modifier
                    .bottomNavBarSelectedItemIndicatorModifier(
                        layoutWidth = layoutWidth,
                        layoutHeight = layoutHeight,
                        state = state
                    )
            )

        }

    }

}