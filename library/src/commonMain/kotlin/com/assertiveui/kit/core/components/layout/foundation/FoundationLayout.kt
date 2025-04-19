/*
 * Copyright 2024 Assertive UI (assertiveui.com)
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

package com.assertiveui.kit.core.components.layout.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.offset
import com.assertiveui.kit.core.theme.Theme
import com.assertiveui.kit.core.theme.color.palette.LocalContentColor
import com.assertiveui.kit.core.utils.LocalWindowState

/**
 *
 * Foundation Layout is used for implementing an organized UI hierarchy.
 *
 * It provides the necessary functionality to put together several UI components
 * in order to construct an organized UI hierarchy, by ensuring proper layout strategy
 * by making an assertion based on the provided local context.
 *
 * @param modifier a [Modifier] to be applied to the scaffold
 * @param topBar a top app bar component, typically a [TopBar]
 * @param bottomBar a bottom bar component, typically a [NavigationBar]
 * @param sideRail a side rail component, typically a [NavigationRail]
 * @param snackbarHost a component to host [Snackbar]s that are pushed to be shown via
 * [SnackbarHostState.showSnackbar], typically a [SnackbarHost].
 * @param containerColor a color used for the background of the scaffold. Use [Color.Transparent]
 * to have no color.
 * @param contentColor a preferred color for the content inside the scaffold. Defaults to either the
 * matching content color for [containerColor], or to the current [LocalContentColor] if
 * [containerColor] is not a color from the theme.
 * @param contentWindowInsets a window insets to be passed to [content] slot via [PaddingValues]
 * params. Scaffold will take the insets into account from the top/bottom/start only if the [topBar]/
 * [bottomBar]/[sideRail] are not present, as the scaffold expect [topBar]/[bottomBar]/[sideRail]
 * to handle insets instead.
 * @param content the main content of the screen, e.g. a [LazyColumn]. The lambda receives a [PaddingValues] that should be
 * applied to the content root via [Modifier.padding] and [Modifier.consumeWindowInsets] to
 * properly offset top / bottom bars and side rail. If using [Modifier.verticalScroll], apply this modifier to
 * the child of the scroll, and not on the scroll itself.
 *
 */
@Composable
fun FoundationLayout(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    sideRail: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    containerColor: Color = Theme.colorPalette.surfaceElevationLow,
    contentColor: Color = Theme.colorPalette.onSurfaceElevationLow,
    contentWindowInsets: WindowInsets = WindowInsets.systemBars,
    content: @Composable (PaddingValues) -> Unit
) = BoxWithConstraints(
    modifier = Modifier
        .background(containerColor)
        .then(modifier),
    content = {

        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            content = {

                val isCompactWidth = LocalWindowState.current.isCompactWidth

                FoundationLayoutImpl(
                    topBar = topBar,
                    bottomBar = if (isCompactWidth) bottomBar else remember { {} },
                    sideRail = if (!isCompactWidth) sideRail else remember { {} },
                    snackbar = snackbarHost,
                    content = content,
                    contentWindowInsets = contentWindowInsets,
                )

            }
        )

    }
)

@Composable
private fun FoundationLayoutImpl(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    sideRail: @Composable () -> Unit,
    snackbar: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit,
    contentWindowInsets: WindowInsets
) = SubcomposeLayout { constraints ->

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    layout(
        width = layoutWidth, //.coerceIn(0, 16777215),
        height = layoutHeight //.coerceIn(0, 16777215)
    ) {

        // Defining side rail placeables.
        val sideRailPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.SideRail,
            content = sideRail
        ).map { it.measure(looseConstraints) }

        val sideRailWidth = sideRailPlaceables.maxByOrNull { it.width }?.width
        val sideRailHeight = sideRailPlaceables.maxByOrNull { it.height }?.height

        // Defining top bar placeables & height.
        val topBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.TopBar,
            content = topBar
        ).map {

            val leftInset = if (
                layoutDirection == LayoutDirection.Ltr
                && sideRailWidth != null
            ) sideRailWidth else 0

            val rightInset = if (
                layoutDirection == LayoutDirection.Rtl
                && sideRailWidth != null
            ) sideRailWidth else 0

            it.measure(
                constraints = looseConstraints.offset(
                    horizontal = (-leftInset) - rightInset,
                )
            )

        }
        val topBarHeight = topBarPlaceables.maxByOrNull { it.height }?.height ?: 0

        // Defining snackbar placeables, width & height.
        val snackbarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.Snackbar,
            content = snackbar
        ).map {

            val leftInset = sideRailWidth ?: contentWindowInsets.getLeft(
                density = this@SubcomposeLayout,
                layoutDirection = layoutDirection
            )

            val rightInset = contentWindowInsets.getRight(
                density = this@SubcomposeLayout,
                layoutDirection = layoutDirection
            )

            val bottomInset = contentWindowInsets.getBottom(
                density = this@SubcomposeLayout
            )

            it.measure(
                constraints = looseConstraints.offset(
                    horizontal = (-leftInset) - rightInset,
                    vertical = (-bottomInset)
                )
            )

        }

        val snackbarHeight = snackbarPlaceables.maxByOrNull { it.height }?.height ?: 0
        val snackbarWidth = (snackbarPlaceables.maxByOrNull { it.width }?.width ?: 0) -
                (sideRailWidth ?: 0)

        // Defining bottom bar placeables & height.
        val bottomBarPlaceables = subcompose(
            slotId = ResponsiveScaffoldLayoutContent.BottomBar,
            content = bottomBar
        ).map { it.measure(looseConstraints) }
        val bottomBarHeight = bottomBarPlaceables.maxByOrNull { it.height }?.height

        // Snackbar bottom offset.
        val snackbarOffsetFromBottom = if (snackbarHeight != 0) {
            snackbarHeight +
                    (bottomBarHeight
                        ?: contentWindowInsets.getBottom(this@SubcomposeLayout))
        } else 0

        // Defining body content placeables.
        val bodyContentPlaceables = subcompose(ResponsiveScaffoldLayoutContent.MainContent) {
            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = if (topBarPlaceables.isEmpty()) insets.calculateTopPadding()
                else topBarHeight.toDp(),
                bottom = if (bottomBarPlaceables.isEmpty() || bottomBarHeight == null) {
                    insets.calculateBottomPadding()
                } else bottomBarHeight.toDp(),
                start = if (sideRailPlaceables.isEmpty() || sideRailWidth == null) {
                    insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection)
                } else sideRailWidth.toDp(),
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection)
            )
            content(innerPadding)
        }.map { it.measure(looseConstraints) }

        /*
         * Defining each content placeables position offset.
         */

        // Defining the top bar placeables position offset.
        val topBarPlaceablesOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) sideRailWidth ?: 0 else 0,
            y = 0
        )

        // Defining the snackbar placeables position offset.
        val snackbarPlaceablesPositionOffset = IntOffset(
            x = (layoutWidth - snackbarWidth) / 2 + contentWindowInsets.getLeft(
                density = this@SubcomposeLayout,
                layoutDirection = layoutDirection
            ),
            y = layoutHeight - snackbarOffsetFromBottom
        )

        // Defining the bottom bar placeables position offset.
        val bottomBarPlaceablesPositionOffset = IntOffset(
            x = 0,
            y = layoutHeight - (bottomBarHeight ?: 0)
        )

        // Defining the side rail placeables position offset.
        val sideRailPlaceablesPositionOffset = IntOffset(
            x = if (layoutDirection == LayoutDirection.Ltr) 0
            else layoutWidth - (sideRailWidth ?: 0),
            y = (layoutHeight / 2) - ((sideRailHeight?.div(2)) ?: 0)
        )

        /*
         * Placing the placeables by maintaining the layout hierarchy:
         * MainContent >> TopBar >> Snackbar >> BottomBar >> SideRail
         */

        // Placing the body content placeables.
        bodyContentPlaceables.forEach { it.place(position = IntOffset.Zero) }

        // Placing the top bar placeables.
        topBarPlaceables.forEach { it.place(position = topBarPlaceablesOffset) }

        // Placing the snackbar placeables.
        snackbarPlaceables.forEach { it.place(position = snackbarPlaceablesPositionOffset) }

        // Placing the bottom bar placeables.
        bottomBarPlaceables.forEach { it.place(position = bottomBarPlaceablesPositionOffset) }

        // Placing the side rail placeables.
        sideRailPlaceables.forEach { it.place(position = sideRailPlaceablesPositionOffset) }

    }

}

/** Constants defining [FoundationLayout]'s content hierarchy slots. */
private enum class ResponsiveScaffoldLayoutContent {
    MainContent, TopBar, Snackbar, BottomBar, SideRail;
}