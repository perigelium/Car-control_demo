package eu.vctrl4.presentation.ui.onlineboard.map.view_model

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.entities.BoardOrder

sealed interface VehicleTrackEvent : ViewEvent
{
    data class InitWithArgs(val order: BoardOrder?) : VehicleTrackEvent
    object ViewIsReady : VehicleTrackEvent
}