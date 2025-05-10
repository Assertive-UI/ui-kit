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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.assertiveui.kit.core.components.siderail.navigation.SideNavRailUtils.sideNavRailContainerModifier
import com.assertiveui.kit.core.components.siderail.navigation.SideNavRailUtils.sideNavRailItemContainerModifier
import com.assertiveui.kit.core.components.siderail.navigation.SideNavRailUtils.sideNavRailSelectedItemIndicatorModifier
import com.assertiveui.kit.core.components.siderail.navigation.item.SideNavRailItemScope

/**
 * A customizable Side Navigation Rail composable that arranges navigation items vertically,
 * and displays a smooth animated indicator for the currently selected item.
 *
 * The navigation rail automatically handles system bar insets (e.g., gesture navigation padding)
 * and adjusts its layout based on the provided [SideNavRailState].
 *
 * @param modifier Optional [Modifier] for additional customization outside the default layout behavior.
 * @param state The [SideNavRailState] representing the current navigation state,
 *              including the selected item index and total item count.
 * @param items A [SideNavRailItemScope] lambda used to define the navigation items displayed in the bar.
 * @param windowInsets Optional [WindowInsets] to apply system padding. Defaults to system bars (vertical + start).
 */
@Composable
fun SideNavRail(
    modifier: Modifier = Modifier,
    state: SideNavRailState,
    items: SideNavRailItemScope,
    windowInsets: WindowInsets = SideNavRailUtils.windowInsets()
) {

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {

        BoxWithConstraints(
            modifier = Modifier
                .sideNavRailContainerModifier(windowInsets)
        ) {

            val layoutHeight by SideNavRailUtils.containerHeight(state)
            val layoutWidth = constraints.maxWidth

            Column(
                modifier = Modifier.sideNavRailItemContainerModifier(),
                content = items
            )

            Box(
                modifier = Modifier
                    .sideNavRailSelectedItemIndicatorModifier(
                        layoutWidth = layoutWidth,
                        layoutHeight = layoutHeight,
                        state = state
                    )
            )

        }

    }

}