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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState

/**
 * Remembers and updates the [BottomNavBarState] based on the given parameters.
 *
 * This function ensures that the state remains consistent across recompositions,
 * while also reflecting any updated `itemsCount` or `selectedItemIndex` values.
 *
 * @param itemsCount The total number of items in the bottom navigation bar.
 * @param selectedItemIndex The index of the currently selected item.
 *
 * @return A [State] containing the current [BottomNavBarState].
 */
@Composable
fun rememberBottomNavBarState(
    itemsCount: Int,
    selectedItemIndex: Int,
): State<BottomNavBarState> {
    return rememberUpdatedState(
        BottomNavBarState(
            itemsCount = itemsCount,
            selectedItemIndex = selectedItemIndex
        )
    )
}

/**
 * Holds the state for a Bottom Navigation Bar, including the number of items
 * and the currently selected item index.
 *
 * This class is marked as [Stable], meaning it can be efficiently observed
 * and composed in Jetpack Compose UI.
 *
 * @property itemsCount The total number of items available in the bottom navigation bar.
 * @property selectedItemIndex The index of the item that is currently selected.
 */
@Stable
class BottomNavBarState(
    val itemsCount: Int,
    val selectedItemIndex: Int,
) {

    /**
     * Checks if two [BottomNavBarState] instances are logically equal based on their
     * `itemsCount` and `selectedItemIndex` values.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is BottomNavBarState) return false
        if (itemsCount != other.itemsCount) return false
        if (selectedItemIndex != other.selectedItemIndex) return false
        return true
    }

    /**
     * Calculates the hash code based on [itemsCount] and [selectedItemIndex].
     */
    override fun hashCode(): Int {
        var result = itemsCount.hashCode()
        result += selectedItemIndex.hashCode()
        return result
    }

    /**
     * Returns a human-readable string representation of the [BottomNavBarState].
     */
    override fun toString(): String {
        return "BottomNavBarState(" +
                "itemsCount=$itemsCount, " +
                "selectedItemIndex=$selectedItemIndex" +
                ")"
    }

}