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
import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.lerp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.assertiveui.kit.core.theme.Theme
import com.assertiveui.kit.core.theme.color.palette.LocalContentColor
import com.assertiveui.kit.core.theme.shape.squircle.SquircleShape
import com.assertiveui.kit.core.utils.transformFraction
import com.assertiveui.kit.core.utils.triangleFraction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun CollapsingTopBar(
    modifier: Modifier = Modifier,
    title: String,
    primaryAction: @Composable (RowScope.() -> Unit)? = null,
    secondaryActions: @Composable (RowScope.() -> Unit)? = null,
    decoration: @Composable @UiComposable (BoxWithConstraintsScope.(progress: Float) -> Unit)? = {
        CollapsingTopBarDefaultDecoration(it)
    },
    scrollBehavior: TopBarScrollBehavior? = null,
    windowInsets: WindowInsets = TopBarUtils.windowInsets(),
    animateTitle: Boolean = false
) = CollapsingTopBarLayout(
    modifier = modifier,
    titleText = title,
    primaryAction = primaryAction,
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

@Composable
fun BoxWithConstraintsScope.CollapsingTopBarDefaultDecoration(progress: Float) {

    val alpha by animateFloatAsState(
        targetValue = transformFraction(
            value = 1f - progress.coerceIn(0f, .5f),
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

@Composable
private fun CollapsingTopBarLayout(
    modifier: Modifier,
    largeTitleTextStyle: TextStyle,
    smallTitleTextStyle: TextStyle,
    titleText: String,
    primaryAction: @Composable (RowScope.() -> Unit)?,
    secondaryActions: @Composable (RowScope.() -> Unit)?,
    decoration: @Composable @UiComposable (BoxWithConstraintsScope.(progress: Float) -> Unit)?,
    windowInsets: WindowInsets,
    backgroundColor: Color,
    contentColor: Color,
    maxHeight: Dp,
    pinnedHeight: Dp,
    scrollBehavior: TopBarScrollBehavior?,
    animateTitle: Boolean
) = CompositionLocalProvider(LocalContentColor provides contentColor) {

    val coroutineScope = rememberCoroutineScope()

    val animatableHeightOffset = remember {
        Animatable(scrollBehavior?.state?.heightOffset ?: 0f)
    }

    // Sync the animatable's value back into the state on change
    LaunchedEffect(animatableHeightOffset.value) {
        scrollBehavior?.state?.heightOffset = animatableHeightOffset.value
    }

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

    val statusBarHeight by remember(statusBarHeightPx) {
        derivedStateOf {
            with(density) {
                statusBarHeightPx.toDp()
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
        if (scrollBehavior != null && !scrollBehavior.isPinned) {
            coroutineScope.launch {
                val newOffset = (animatableHeightOffset.value + delta)
                    .coerceIn(scrollBehavior.state.heightOffsetLimit, 0f)
                animatableHeightOffset.snapTo(newOffset)
            }
        }
    }

    val onDragStopped = remember<suspend CoroutineScope.(Float) -> Unit>(scrollBehavior) {
        { _ ->
            if (scrollBehavior != null && !scrollBehavior.isPinned) {

                val fraction = scrollBehavior.state.collapsedFraction
                val targetOffset = if (fraction > 0.5f) {
                    scrollBehavior.state.heightOffsetLimit // Fully collapsed
                } else {
                    0f // Fully expanded
                }


            }
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .clipToBounds()
            .background(color = backgroundColor)
            .draggable(
                state = draggableState,
                orientation = Orientation.Vertical,
                onDragStopped = onDragStopped,
                startDragImmediately = false
            ),
        contentAlignment = Alignment.CenterStart,
        content = {

            if (decoration != null) {
                decoration(collapsedFraction())
            }

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

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        top = windowInsets
                            .asPaddingValues()
                            .calculateTopPadding() + 16.dp
                    )
                    .padding(horizontal = horizontalPadding)
                    .fillMaxSize()
                    .clip(shape)
                    .background(bgColor),
                verticalAlignment = Alignment.Bottom
            ) {

                if (primaryAction != null) {
                    Row(
                        modifier = Modifier
                            .height(pinnedHeight)
                            .padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = primaryAction
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (secondaryActions != null) {
                    Row(
                        modifier = Modifier
                            .height(pinnedHeight)
                            .padding(end = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        content = secondaryActions
                    )
                }

            }

            val windowInsetsPadding by rememberUpdatedState(
                windowInsets.only(WindowInsetsSides.Horizontal)
            )

            val topPadding by animateDpAsState(
                targetValue = transformFraction(
                    value = collapsedFraction(),
                    startX = 0f,
                    endX = 1f,
                    startY = 0.dp.value,
                    endY = 16.dp.value
                ).dp,
                animationSpec = snap()
            )

            val startPadding by animateDpAsState(
                targetValue = transformFraction(
                    value = collapsedFraction().coerceAtMost(.67f),
                    startX = 0f,
                    endX = .67f,
                    startY = 32.dp.value,
                    endY = 48.dp.value + (if (primaryAction == null) 0.dp.value else 40.dp.value)
                ).dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessHigh
                )
            )

            val titleColor by animateColorAsState(
                targetValue = contentColor.copy(
                    alpha = 1f - triangleFraction(collapsedFraction())
                ),
                animationSpec = snap()
            )

            Box(
                modifier = Modifier
                    .windowInsetsPadding(windowInsetsPadding)
                    .padding(top = statusBarHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {

                ProvideTextStyle(
                    value = titleTextStyle,
                    content = {

                        BasicText(
                            modifier = Modifier
                                .padding(
                                    top = topPadding,
                                    start = startPadding,
                                    end = 16.dp
                                )
                                .then(
                                    if (animateTitle) Modifier.animateContentSize(
                                        animationSpec = spring(),
                                        alignment = Alignment.Center
                                    ) else Modifier
                                ),
                            text = titleText,
                            style = titleTextStyle.copy(
                                color = titleColor,
                                textAlign = TextAlign.Start
                            )
                        )

                    }
                )

            }

        }
    )

}