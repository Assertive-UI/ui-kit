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

package com.assertiveui.kit.core.components.topbar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.components.topbar.action.TopBarActionsScope
import com.assertiveui.kit.core.theme.Theme
import com.assertiveui.kit.core.theme.color.palette.LocalContentColor
import com.assertiveui.kit.core.theme.shape.squircle.SquircleShape
import com.assertiveui.kit.core.utils.transformFraction
import kotlinx.coroutines.CoroutineScope
import kotlin.math.roundToInt

/**
 *
 * A collapsible top bar that smoothly expands and contracts based on scroll position.
 *
 * This component is designed to provide a flexible top bar that reacts to
 * scroll behaviors or user interactions. It supports optional action slots, dynamic decoration layers,
 * and animated title transitions.
 *
 * @param modifier The [Modifier] to be applied to the top bar container.
 * @param title The text to display as the main title. The title smoothly animates its style as the bar collapses.
 * @param primaryActions Optional composable content to be displayed on the start side (usually the left)
 * of the top bar. Commonly used for navigation buttons or leading actions.
 * @param secondaryActions Optional composable content to be displayed on the end side (usually the right)
 * of the top bar. Commonly used for action icons such as search, settings, etc.
 * @param decoration An optional visual layer rendered behind the top bar content. Useful for background
 * effects such as gradients, shapes, or parallax elements. The `progress` value (from 0f expanded to 1f collapsed)
 * is passed for dynamic visuals. By default, [DefaultCollapsibleTopBarDecoration] is used.
 * @param scrollBehavior A [TopBarScrollBehavior] that links the top bar to scroll events
 * in the layout. This drives the collapsing animation based on scroll.
 * @param windowInsets [WindowInsets] that define how the top bar handles system padding (status bar, horizontal insets).
 * Default is [TopBarUtils.windowInsets], which includes horizontal and status bar padding.
 * @param animateTitle If true, the title will animate its size/position on collapse using `animateContentSize()`.
 * If false, title changes are applied immediately without animation.
 *
 * Example usage:
 * ```kotlin
 *     val scrollBehavior = TopBarUtils.exitUntilCollapsedScrollBehavior()
 *
 *     FoundationLayout(
 *         modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
 *         topBar = {
 *
 *             CollapsingTopBar(
 *                 title = "Title",
 *                 scrollBehavior = scrollBehavior
 *             )
 *
 *         }
 *     ) { safePadding ->
 *
 *         LazyColumn(
 *             modifier = Modifier.fillMaxSize(),
 *             state = lazyListState,
 *             contentPadding = safePadding
 *         ) {
 *
 *             // ...
 *
 *         }
 *
 *     }
 * ```
 *
 */
@Composable
fun CollapsibleTopBar(
    modifier: Modifier = Modifier,
    title: String,
    primaryActions: TopBarActionsScope? = null,
    secondaryActions: TopBarActionsScope? = null,
    decoration: CollapsibleTopBarDecorationScope? = DefaultCollapsibleTopBarDecoration,
    scrollBehavior: TopBarScrollBehavior?,
    windowInsets: WindowInsets = TopBarUtils.windowInsets(),
    animateTitle: Boolean = true
) {

    CollapsibleTopBarLayoutImpl(
        modifier = modifier,
        titleText = title,
        primaryActions = primaryActions,
        secondaryActions = secondaryActions,
        decoration = decoration,
        scrollBehavior = scrollBehavior,
        windowInsets = windowInsets,
        smallTitleTextStyle = Theme.typefaces.titleSmall,
        largeTitleTextStyle = Theme.typefaces.titleLarge,
        backgroundColor = Color.Transparent,
        contentColor = Theme.colorPalette.onSurfaceElevationHigh,
        maxHeight = TopBarUtils.largeContainerHeight(scrollBehavior),
        pinnedHeight = TopBarUtils.smallContainerHeight,
        animateTitle = animateTitle
    )

}

/**
 *
 * A default decorative visual used behind the [CollapsibleTopBar], showcasing floating circles that
 * fade in as the top bar collapses.
 *
 * This implementation provides a subtle, animated effect that enhances the top bar's visual depth and
 * transition. Two circular borders appear near the top end of the bar as the collapsing progress increases.
 *
 * The alpha of each element is interpolated based on the `progress()` value, animating from fully transparent
 * (when expanded) to semi-visible (as the bar collapses up to 50%).
 *
 * This decoration is designed to:
 * - Appear gradually between 0% and 50% collapse
 * - Use [animateFloatAsState] with a [snap] animation for quick transitions
 * - Apply `graphicsLayer` alpha and smooth clipping
 * - Respect system status bar padding and position itself appropriately
 *
 * Can be used as a drop-in `decoration` value for [CollapsibleTopBar].
 *
 * Example usage:
 * ```kotlin
 * CollapsingTopBar(
 *     title = "Title",
 *     decoration = DefaultCollapsibleTopBarDecoration,
 *     scrollBehavior = scrollBehavior
 * )
 * ```
 *
 */
val DefaultCollapsibleTopBarDecoration: CollapsibleTopBarDecorationScope = { progress ->

    val alpha by animateFloatAsState(
        targetValue = transformFraction(
            value = 1f - progress().coerceIn(0f, .5f),
            startX = 1f,
            endX = .5f,
            startY = 0f,
            endY = 1f
        ),
        animationSpec = snap()
    )

    Box(
        modifier = Modifier
            .graphicsLayer {
                this.alpha = alpha
                    .div(2)
                    .coerceAtLeast(0f)
            }
            .statusBarsPadding()
            .padding(end = 48.dp, top = 32.dp)
            .size(32.dp)
            .align(Alignment.TopEnd)
            .border(
                width = 2.dp,
                color = Theme.colorPalette.base,
                shape = CircleShape
            )
    )

    Box(
        modifier = Modifier
            .graphicsLayer { this.alpha = alpha }
            .statusBarsPadding()
            .padding(end = 32.dp, top = 48.dp)
            .size(32.dp)
            .align(Alignment.TopEnd)
            .border(
                width = 2.dp,
                color = Theme.colorPalette.base,
                shape = CircleShape
            )
    )

}

/**
 *
 * A composable scope used for defining dynamic decorations behind a [CollapsibleTopBar].
 *
 * This typealias represents a composable lambda that is invoked inside a [BoxWithConstraintsScope],
 * and receives a function to access the current `collapsedProgress` of the top bar. This allows
 * the decoration to be driven by scroll-based progress, enabling parallax effects, animated shapes,
 * gradients, or other visuals that respond to the bar's state.
 *
 * The `progress()` value ranges from `0f` (fully expanded) to `1f` (fully collapsed), and is safe
 * to use in animations or conditional logic.
 *
 * Example usage:
 * ```kotlin
 * val decoration: CollapsingTopBarDecorationScope = { progress ->
 *     val scale = lerp(1f, 0.8f, progress())
 *     Box(
 *         modifier = Modifier
 *             .align(Alignment.TopCenter)
 *             .scale(scale)
 *             .background(Color.Blue, shape = CircleShape)
 *             .size(48.dp)
 *     )
 * }
 * ```
 *
 */
typealias CollapsibleTopBarDecorationScope =
        @Composable @UiComposable (BoxWithConstraintsScope.(progress: () -> Float) -> Unit)

/**
 *
 * Core layout implementation for the [CollapsibleTopBar], responsible for measuring and arranging
 * the top bar's visual elements based on the scroll behavior, insets, and collapse progress.
 *
 * This layout handles:
 * - Title interpolation between large and small styles
 * - Drag behavior that adjusts height based on scroll
 * - Action slots on the start and end of the top bar
 * - Optional decorative visuals tied to the collapse progress
 * - Dynamic height calculation and animation
 *
 * Intended for internal use by higher-level APIs like [CollapsibleTopBar]. Not typically used directly.
 *
 * @param modifier Modifier applied to the entire top bar layout.
 * @param largeTitleTextStyle The [TextStyle] used when the top bar is fully expanded.
 * @param smallTitleTextStyle The [TextStyle] used when the top bar is fully collapsed.
 * @param titleText The text displayed as the top bar's title. Will animate between styles if [animateTitle] is true.
 * @param primaryActions Optional content displayed on the start side (typically the left) of the top bar.
 * @param secondaryActions Optional content displayed on the end side (typically the right) of the top bar.
 * @param decoration Optional background decoration rendered behind the content. Receives current collapse progress.
 * @param windowInsets Insets to apply for system UI (e.g. status bar, horizontal padding).
 * @param backgroundColor The color behind all top bar content and decoration.
 * @param contentColor The color applied to the title and action icons.
 * @param maxHeight The maximum height of the top bar (fully expanded).
 * @param pinnedHeight The pinned (minimum) height of the top bar (fully collapsed).
 * @param scrollBehavior An optional scroll behavior that drives expansion and collapse.
 * @param animateTitle If true, the title will animate style and size during collapse/expansion transitions.
 *
 */
@Composable
private fun CollapsibleTopBarLayoutImpl(
    modifier: Modifier,
    largeTitleTextStyle: TextStyle,
    smallTitleTextStyle: TextStyle,
    titleText: String,
    primaryActions: TopBarActionsScope?,
    secondaryActions: TopBarActionsScope?,
    decoration: CollapsibleTopBarDecorationScope?,
    windowInsets: WindowInsets,
    backgroundColor: Color,
    contentColor: Color,
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopBarScrollBehavior?,
    animateTitle: Boolean
) = CompositionLocalProvider(LocalContentColor provides contentColor) {

    val density = LocalDensity.current
    val statusBarHeightPx: Float
    val pinnedHeightPx: Float
    val maxHeightPx: Float

    density.run {

        statusBarHeightPx = windowInsets.getTop(this).toFloat()
        pinnedHeightPx = pinnedHeight.toPx() + statusBarHeightPx
        maxHeightPx = maxHeight.toPx() + statusBarHeightPx

        if (scrollBehavior?.state?.heightOffsetLimit != pinnedHeightPx - maxHeightPx) {
            scrollBehavior?.state?.heightOffsetLimit = pinnedHeightPx - maxHeightPx
        }

    }

    val collapsedFraction by rememberUpdatedState {
        scrollBehavior?.state?.collapsedFraction ?: 0f
    }

    val height by remember(
        density,
        maxHeightPx,
        scrollBehavior?.state?.heightOffset
    ) {
        derivedStateOf {
            with(density) {
                (maxHeightPx + (scrollBehavior?.state?.heightOffset ?: 0f)).toDp()
                    .coerceAtLeast(0.dp)
            }
        }
    }

    val titleTextStyle by remember(
        collapsedFraction,
        smallTitleTextStyle,
        largeTitleTextStyle
    ) {
        derivedStateOf {
            lerp(
                start = largeTitleTextStyle,
                stop = smallTitleTextStyle,
                fraction = collapsedFraction()
            )
        }
    }

    val draggableState = rememberDraggableState { delta ->
        if (
            scrollBehavior != null
            && !scrollBehavior.isPinned
            && scrollBehavior.canScroll()
        ) {
            scrollBehavior.state.heightOffset += delta
        }
    }

    val onDragStopped = remember<suspend CoroutineScope.(Float) -> Unit>(scrollBehavior) {
        { velocity ->
            if (scrollBehavior != null && !scrollBehavior.isPinned) {
                settleAppBar(
                    state = scrollBehavior.state,
                    velocity = velocity,
                    flingAnimationSpec = scrollBehavior.flingAnimationSpec,
                    snapAnimationSpec = scrollBehavior.snapAnimationSpec
                )
            }
        }
    }

    SubcomposeLayout(
        modifier = Modifier
            .height(height)
            .clipToBounds()
            .background(color = backgroundColor)
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = onDragStopped,
                startDragImmediately = false
            )
            .then(modifier)
    ) { constraints ->

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        layout(
            width = layoutWidth,
            height = layoutHeight
        ) {

            // Defining decoration placeables.
            val decorationPlaceables = subcompose(
                slotId = CollapsibleTopBarPlaceables.Decoration,
                content = {

                    if (decoration != null) BoxWithConstraints(
                        modifier = Modifier
                            .size(
                                width = layoutWidth.toDp(),
                                height = layoutHeight.toDp()
                            ),
                        content = { decoration(collapsedFraction) }
                    ) else Unit

                }
            ).map { it.measure(looseConstraints) }

            // Defining background area placeables.
            val backgroundAreaPlaceables = subcompose(
                slotId = CollapsibleTopBarPlaceables.BackgroundArea,
                content = {

                    val horizontalPadding by animateDpAsState(
                        targetValue = transformFraction(
                            value = collapsedFraction(),
                            startX = 0f,
                            endX = 1f,
                            startY = 16.dp.value,
                            endY = 24.dp.value
                        ).dp,
                        animationSpec = snap()
                    )

                    val bgColor by animateColorAsState(
                        targetValue = Theme.colorPalette.surfaceElevationHigh.copy(
                            alpha = transformFraction(
                                value = collapsedFraction().coerceIn(.5f, 1f),
                                startX = .5f,
                                endX = 1f,
                                startY = 0f,
                                endY = 1f
                            )
                        ),
                        animationSpec = snap()
                    )

                    val borderColor by animateColorAsState(
                        targetValue = Theme.colorPalette.surfaceElevationLow.copy(
                            alpha = transformFraction(
                                value = collapsedFraction().coerceIn(.5f, 1f),
                                startX = .5f,
                                endX = 1f,
                                startY = 0f,
                                endY = .2f
                            )
                        ),
                        animationSpec = snap()
                    )

                    val shapeSmoothing by animateIntAsState(
                        targetValue = (transformFraction(
                            value = 1f - collapsedFraction(),
                            startX = 1f,
                            endX = 0f,
                            startY = .67f,
                            endY = .45f
                        ) * 100).roundToInt(),
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )

                    val shape by rememberUpdatedState(
                        SquircleShape(32.dp, shapeSmoothing)
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = windowInsets
                                    .asPaddingValues()
                                    .calculateTopPadding() + 16.dp,
                                bottom = 16.dp
                            )
                            .padding(horizontal = horizontalPadding)
                            .fillMaxSize()
                            .clip(shape)
                            .background(bgColor)
                            .border(
                                width = 1.dp,
                                color = borderColor,
                                shape = shape
                            )
                    )

                }
            ).map { it.measure(looseConstraints) }

            // Defining primary actions placeables.
            val primaryActionsPlaceables = subcompose(
                slotId = CollapsibleTopBarPlaceables.PrimaryActions,
                content = {

                    if (primaryActions != null) {

                        val startPadding by animateDpAsState(
                            targetValue = transformFraction(
                                value = collapsedFraction(),
                                startX = 0f,
                                endX = 1f,
                                startY = 24.dp.value,
                                endY = 32.dp.value
                            ).dp,
                            animationSpec = snap()
                        )

                        Row(
                            modifier = Modifier
                                .height(pinnedHeight)
                                .padding(start = startPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            content = primaryActions
                        )

                    } else Unit

                }
            ).map { it.measure(looseConstraints) }

            val primaryActionsWidth = primaryActionsPlaceables
                .maxByOrNull { it.width }?.width ?: 0

            val primaryActionsHeight = primaryActionsPlaceables
                .maxByOrNull { it.height }?.height ?: 0

            // Defining secondary actions placeables.
            val secondaryActionsPlaceables = subcompose(
                slotId = CollapsibleTopBarPlaceables.SecondaryActions,
                content = {

                    if (secondaryActions != null) {

                        val startPadding by animateDpAsState(
                            targetValue = transformFraction(
                                value = collapsedFraction(),
                                startX = 0f,
                                endX = 1f,
                                startY = 24.dp.value,
                                endY = 32.dp.value
                            ).dp,
                            animationSpec = snap()
                        )

                        Row(
                            modifier = Modifier
                                .height(pinnedHeight)
                                .padding(end = startPadding),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            content = secondaryActions
                        )

                    } else Unit

                }
            ).map { it.measure(looseConstraints) }

            val secondaryActionsWidth = secondaryActionsPlaceables
                .maxByOrNull { it.width }?.width ?: 0

            val secondaryActionsHeight = secondaryActionsPlaceables
                .maxByOrNull { it.height }?.height ?: 0

            // Defining title placeables.
            val titlePlaceables = subcompose(
                slotId = CollapsibleTopBarPlaceables.Title,
                content = {

                    val windowInsetsPadding by rememberUpdatedState(
                        windowInsets.only(WindowInsetsSides.Top)
                    )

                    val startPadding by animateDpAsState(
                        targetValue = transformFraction(
                            value = collapsedFraction().coerceAtMost(.67f),
                            startX = 0f,
                            endX = .67f,
                            startY = 32.dp.value,
                            endY = 16.dp.value + (if (primaryActions == null) 32.dp.value
                            else primaryActionsWidth.toDp().value)
                        ).dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )

                    val endPadding by animateDpAsState(
                        targetValue = transformFraction(
                            value = collapsedFraction().coerceAtMost(.67f),
                            startX = 0f,
                            endX = .67f,
                            startY = 32.dp.value,
                            endY = 16.dp.value + (if (secondaryActions == null) 32.dp.value
                            else primaryActionsWidth.toDp().value)
                        ).dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )

                    val topPadding by animateDpAsState(
                        targetValue = transformFraction(
                            value = .67f - collapsedFraction().coerceAtMost(.67f),
                            startX = .67f,
                            endX = 0f,
                            startY = 24.dp.value,
                            endY = 0.dp.value
                        ).dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessHigh
                        )
                    )

                    val bottomPadding by animateDpAsState(
                        targetValue = transformFraction(
                            value = .67f - collapsedFraction().coerceAtMost(.67f),
                            startX = .67f,
                            endX = 0f,
                            startY = primaryActionsHeight.toDp().value,
                            endY = 0.dp.value
                        ).dp,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    )

                    Box(
                        modifier = Modifier
                            .then(
                                if (animateTitle) Modifier.animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioNoBouncy,
                                        stiffness = Spring.StiffnessHigh
                                    ),
                                    alignment = Alignment.Center
                                ) else Modifier
                            )
                            .windowInsetsPadding(windowInsetsPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.CenterStart,
                        content = {

                            BasicText(
                                modifier = Modifier
                                    .padding(
                                        top = topPadding,
                                        start = startPadding,
                                        end = endPadding,
                                        bottom = bottomPadding
                                    ),
                                text = titleText,
                                style = titleTextStyle.copy(
                                    color = contentColor,
                                    textAlign = TextAlign.Start
                                ),
                                overflow = TextOverflow.Ellipsis
                            )

                        }
                    )

                }
            ).map { it.measure(looseConstraints) }

            val titleWidth = titlePlaceables
                .maxByOrNull { it.width }?.width ?: 0

            /*
             * Defining each content placeables position offset.
             */

            // Defining the decoration placeables position offset.
            val decorationPlaceablesOffset = IntOffset(
                x = 0,
                y = 0
            )

            // Defining the background area placeables position offset.
            val backgroundAreaPlaceablesOffset = IntOffset(
                x = 0,
                y = 0
            )

            // Defining the primary actions placeables position offset.
            val primaryActionsPlaceablesOffset = IntOffset(
                x = if (layoutDirection == LayoutDirection.Ltr) 0
                else layoutWidth - primaryActionsWidth,
                y = layoutHeight - primaryActionsHeight
            )

            // Defining the secondary actions placeables position offset.
            val secondaryActionsPlaceablesOffset = IntOffset(
                x = if (layoutDirection == LayoutDirection.Rtl) 0
                else layoutWidth - secondaryActionsWidth,
                y = layoutHeight - secondaryActionsHeight
            )

            // Defining the title placeables position offset.
            val titlePlaceablesOffset = IntOffset(
                x = if (layoutDirection == LayoutDirection.Ltr) 0
                else layoutWidth - titleWidth,
                y = 0
            )

            /*
             * Placing the placeables by maintaining the layout hierarchy:
             * Decoration >> BackgroundArea >> PrimaryActions >> SecondaryActions >> Title
             */

            // Placing the decoration placeables.
            decorationPlaceables.forEach {
                it.place(position = decorationPlaceablesOffset)
            }

            // Placing the background area placeables.
            backgroundAreaPlaceables.forEach {
                it.place(position = backgroundAreaPlaceablesOffset)
            }

            // Placing the primary actions placeables.
            primaryActionsPlaceables.forEach {
                it.place(position = primaryActionsPlaceablesOffset)
            }

            // Placing the secondary actions placeables.
            secondaryActionsPlaceables.forEach {
                it.place(position = secondaryActionsPlaceablesOffset)
            }

            // Placing the title placeables.
            titlePlaceables.forEach {
                it.place(position = titlePlaceablesOffset)
            }

        }

    }

}

/**
 *
 * Internal enum representing the distinct layout elements
 * (placeables) of a [CollapsibleTopBarLayoutImpl].
 *
 * These identifiers are used during the layout pass to assign and position each top bar element
 * in a structured and readable way.
 *
 * This enum improves layout readability and stability by clearly separating each composable's role
 * within the top bar's layout logic.
 *
 */
private enum class CollapsibleTopBarPlaceables {

    /**
     * The decorative visual layer placed behind all content, typically used for background animations,
     * parallax elements, or floating shapes.
     */
    Decoration,

    /**
     * The base container area behind the title and actions, used for applying
     * background color or surface elevation when the top bar is collapsed.
     */
    BackgroundArea,

    /**
     * The start-aligned action row (typically the left side in LTR layouts),
     * often used for navigation icons or contextual buttons.
     */
    PrimaryActions,

    /**
     * The end-aligned action row (typically the right side in LTR layouts),
     * used for action icons like search, settings, or overflow menus.
     */
    SecondaryActions,

    /**
     * The title element, which typically animates between large and small styles
     * as the top bar collapses or expands.
     */
    Title;

}