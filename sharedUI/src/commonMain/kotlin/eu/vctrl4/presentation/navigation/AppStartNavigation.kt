package eu.vctrl4.presentation.navigation

import kotlinx.serialization.*

@Serializable
sealed interface AppStartNavigation {

    @Serializable
    data object Welcome : AppStartNavigation

    @Serializable
    data object Login : AppStartNavigation

    @Serializable
    data object Main : AppStartNavigation

    @Serializable
    data object OfferUpdateApplication : AppStartNavigation
}

