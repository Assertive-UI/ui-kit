package com.assertiveui.kit.core.utils

enum class Platform { Android, IOS, Desktop, Web }

expect fun getPlatform(): Platform