package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface AppNavigation {

    @Serializable
    data object AppStart : AppNavigation

    @Serializable
    data object Main : AppNavigation
}

