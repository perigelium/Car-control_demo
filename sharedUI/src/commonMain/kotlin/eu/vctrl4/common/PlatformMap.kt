package eu.vctrl4.common

import androidx.compose.runtime.*
import androidx.compose.ui.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*

@Composable
expect fun PlatformOsmMap(
	viewState: VehicleTrackViewState,
	modifier: Modifier = Modifier
                         )