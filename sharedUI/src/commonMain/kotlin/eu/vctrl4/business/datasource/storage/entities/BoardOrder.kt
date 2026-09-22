package eu.vctrl4.business.datasource.storage.entities

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.utils.*
import kotlinx.serialization.Serializable
import kotlin.uuid.*

@Serializable
class BoardOrder
{
    val NavDeviceId: Int? = null //
    @OptIn(ExperimentalUuidApi::class)
    var OrderId: String = Uuid.random().toString() //	guid
    var OrderNumber: String? = null //
    var Number: String? = null //
    val Picture: String? = null //
    var RentEndDate: String? = null //	timestamp
    var RentStartDate: String? = null //	timestamp
    val RowId: String? = null //	guid
    val Token: String? = null //
    val VehicleId: String? = null //	guid
    val VehicleName: String? = null
    var VehicleNumber: String? = null  
    val Zones: String? = null
    val WialonToken:String? = null
    val WialonURL:String? = null

    val Ticket: Ticket? = null

    val titleTextAttrs: MutableList<TitleTextAttrs> by lazy {
        mutableListOf(
            TitleTextAttrs(
	            title = Res.string.start.asState, strText = DateTimeUtils.reformatDateTime(
		            RentStartDate, DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT, DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
	                                                                                       ) ?: ""
            ), TitleTextAttrs(
		        title = Res.string.end.asState, strText = DateTimeUtils.reformatDateTime(
			        RentEndDate, DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT, DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
		                                                                     ) ?: ""
            )
        )
    }
}
