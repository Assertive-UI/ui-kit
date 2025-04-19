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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.assertiveui.kit.core.theme.getDefaultColorPalette
import com.assertiveui.kit.app.App
import com.assertiveui.kit.lite.core.utils.applyBasicAssertiveUIKitStyle
import com.assertiveui.kit.lite.core.utils.setupEdgeToEdge
import com.stoyanvuchev.systemuibarstweaker.ProvideSystemUIBarsTweaker
import com.stoyanvuchev.systemuibarstweaker.rememberSystemUIBarsTweaker

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupEdgeToEdge()

        setContent {

            val tweaker = rememberSystemUIBarsTweaker()
            val isSystemInDarkTheme = isSystemInDarkTheme()

            var darkTheme by remember { mutableStateOf(isSystemInDarkTheme) }
            val colorPalette by remember { mutableStateOf(getDefaultColorPalette(darkTheme)) }

            DisposableEffect(darkTheme, tweaker) {
                tweaker.applyBasicAssertiveUIKitStyle(
                    darkTheme = darkTheme,
                    lightThemeScrimColor = colorPalette.surfaceElevationLow,
                    darkThemeScrimColor = colorPalette.surfaceElevationLow
                )
                onDispose {}
            }

            ProvideSystemUIBarsTweaker(
                systemUIBarsTweaker = tweaker,
                content = {

                    App(onApplyDarkIcons = remember { { darkTheme = it } })

                }
            )

        }

    }

}