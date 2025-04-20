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

package com.assertiveui.kit.app

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import assertiveuikit.application.generated.resources.Res
import assertiveuikit.application.generated.resources.arrow_left
import assertiveuikit.application.generated.resources.menu
import assertiveuikit.application.generated.resources.search
import com.assertiveui.kit.app.core.ui.AppUIWrapper
import com.assertiveui.kit.core.components.layout.foundation.FoundationLayout
import com.assertiveui.kit.core.components.topbar.CollapsingTopBar
import com.assertiveui.kit.core.components.topbar.TopBarUtils
import com.assertiveui.kit.core.components.topbar.action.TopBarAction
import com.assertiveui.kit.core.theme.Theme
import com.assertiveui.kit.core.theme.ThemeMode
import com.assertiveui.kit.core.theme.color.palette.ColorPaletteModel
import com.assertiveui.kit.core.theme.color.palette.LightColorPaletteTokens
import com.assertiveui.kit.core.theme.color.palette.asAnimatedColorPalette
import com.assertiveui.kit.core.theme.color.utils.ColorUtils
import com.assertiveui.kit.core.theme.isInDarkThemeMode
import org.jetbrains.compose.resources.painterResource
import kotlin.math.roundToInt

@Composable
fun App(onApplyDarkIcons: (Boolean) -> Unit = remember { {} }) {

    var hue by rememberSaveable { mutableIntStateOf(210) }
    val onHueChanged = remember<(Int) -> Unit> {
        { newHue -> hue = newHue }
    }

    var themeMode by remember { mutableStateOf(ThemeMode.System) }
    val darkTheme by rememberUpdatedState(isInDarkThemeMode(themeMode))
    val onThemeMode = remember {
        {
            themeMode = when (themeMode) {
                ThemeMode.Light -> ThemeMode.Dark
                ThemeMode.Dark -> ThemeMode.System
                ThemeMode.System -> ThemeMode.Light
            }
        }
    }

    var colorPaletteModel by remember { mutableStateOf(ColorPaletteModel()) }
    val onColorPaletteModel = remember<suspend (Int) -> Unit> {
        { colorPaletteModel = ColorUtils.generateColorPaletteModel(hue) }
    }

    val colorPalette by rememberUpdatedState(
        (if (darkTheme) colorPaletteModel.darkColorPalette
        else colorPaletteModel.lightColorPalette).asAnimatedColorPalette()
    )

    LaunchedEffect(darkTheme) {
        onApplyDarkIcons(darkTheme)
    }

    LaunchedEffect(hue) {
        onColorPaletteModel(hue)
    }

    AppUIWrapper(
        themeMode = themeMode,
        colorPalette = colorPalette
    ) {

        val scrollBehavior = TopBarUtils.exitUntilCollapsedScrollBehavior()

        FoundationLayout(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {

                CollapsingTopBar(
                    title = "Top Bar",
                    scrollBehavior = scrollBehavior,
                    primaryAction = {

                        TopBarAction(
                            icon = painterResource(Res.drawable.arrow_left),
                            onClick = remember { {} },
                            contentDescription = "Navigate Up"
                        )

                    },
                    secondaryActions = {

                        TopBarAction(
                            icon = painterResource(Res.drawable.search),
                            onClick = remember { {} },
                            contentDescription = "Search"
                        )

                        TopBarAction(
                            icon = painterResource(Res.drawable.menu),
                            onClick = remember { {} },
                            contentDescription = "Menu"
                        )

                    }/*,
                    decoration = { progress ->

                        Image(
                            modifier = Modifier
                                .graphicsLayer { this.alpha = 1f - progress }
                                .matchParentSize(),
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            painter = painterResource(Res.drawable.bg)
                        )

                    }*/
                )

            },
            bottomBar = {

                Box(
                    modifier = Modifier
                        .padding(
                            WindowInsets.systemBars.exclude(
                                WindowInsets.systemBars.only(
                                    WindowInsetsSides.Top
                                )
                            ).asPaddingValues()
                        )
                        .padding(horizontal = 40.dp)
                        .padding(bottom = 24.dp)
                        .fillMaxWidth()
                        .height(64.dp)
                        .clip(Theme.shapes.mediumShape)
                        .background(Theme.colorPalette.surfaceElevationHigh),
                    contentAlignment = Alignment.Center
                ) {

                    BasicText(
                        modifier = Modifier.padding(horizontal = 32.dp),
                        text = "Bottom Bar",
                        style = Theme.typefaces.titleSmall.copy(
                            color = Theme.colorPalette.onSurfaceElevationHigh,
                            textAlign = TextAlign.Center
                        )
                    )

                }

            },
            sideRail = {

                Box(
                    modifier = Modifier
                        .padding(WindowInsets.systemBars.asPaddingValues())
                        .padding(vertical = 64.dp)
                        .padding(horizontal = 24.dp)
                        .width(64.dp)
                        .clip(Theme.shapes.mediumShape)
                        .background(Theme.colorPalette.surfaceElevationHigh),
                    contentAlignment = Alignment.Center
                ) {

                    BasicText(
                        modifier = Modifier.padding(vertical = 32.dp),
                        text = "S\ni\nd\ne\n\nR\na\ni\nl",
                        style = Theme.typefaces.titleSmall.copy(
                            color = Theme.colorPalette.onSurfaceElevationHigh,
                            textAlign = TextAlign.Center
                        )
                    )

                }

            }
        ) { safePadding ->

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = safePadding
            ) {

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {

                        Spacer(modifier = Modifier.height(32.dp))

                        var newHue by remember(hue) { mutableIntStateOf(hue) }
                        val newHueValue by rememberUpdatedState(newHue.toFloat() / 360f)

                        BasicText(
                            text = "Hue:  $newHue",
                            style = Theme.typefaces.bodyLarge.copy(
                                color = Theme.colorPalette.onSurfaceElevationLow
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Slider(
                            modifier = Modifier
                                .widthIn(
                                    min = 256.dp,
                                    max = 512.dp
                                ),
                            value = newHueValue,
                            onValueChange = { newHueFloat ->
                                newHue = (newHueFloat * 360f).roundToInt()
                            },
                            onValueChangeFinished = { onHueChanged(newHue) },
                            colors = SliderDefaults.colors(
                                thumbColor = Theme.colorPalette.base,
                                activeTrackColor = Theme.colorPalette.base,
                                inactiveTrackColor = Theme.colorPalette.outline,
                            )
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {

                            Box(
                                modifier = Modifier
                                    .clip(Theme.shapes.smallShape)
                                    .background(Theme.colorPalette.base)
                                    .clickable(onClick = onThemeMode)
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 12.dp
                                    )
                                    .animateContentSize(),
                                content = {

                                    BasicText(
                                        text = "$themeMode Theme",
                                        style = Theme.typefaces.actionLarge.copy(
                                            color = Theme.colorPalette.onBase
                                        )
                                    )

                                }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            val onReset = remember {
                                { onHueChanged(LightColorPaletteTokens.baseHue) }
                            }

                            Box(
                                modifier = Modifier
                                    .clip(Theme.shapes.smallShape)
                                    .clickable(onClick = onReset)
                                    .padding(
                                        horizontal = 18.dp,
                                        vertical = 12.dp
                                    ),
                                content = {

                                    BasicText(
                                        text = "Reset Hue",
                                        style = Theme.typefaces.actionLarge.copy(
                                            color = Theme.colorPalette.base
                                        )
                                    )

                                }
                            )

                        }

                    }

                }

                items(100) {

                    BasicText(
                        modifier = Modifier
                            .padding(
                                horizontal = 32.dp,
                                vertical = 8.dp
                            ),
                        text = "Item: ${it + 1}",
                        style = Theme.typefaces.bodyMedium.copy(
                            color = Theme.colorPalette.onSurfaceElevationLow
                        )
                    )

                }

            }

        }

    }

}
