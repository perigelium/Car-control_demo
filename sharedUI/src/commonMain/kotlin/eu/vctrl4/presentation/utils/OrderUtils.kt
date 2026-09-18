package eu.vctrl4.presentation.utils

import eu.vctrl4.business.constants.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.presentation.utils.DateTimeUtils.getTodayBeginInMillis
import eu.vctrl4.storage.entities.*
import eu.vctrl4.storage.remote.entities.*
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Instant

object OrderUtils {

	fun isOrderCancellable(orderDetails: OrderDetails): Boolean {
		return "CN"!=orderDetails.State && "RD"!=orderDetails.State
	}

	fun prepareDefaultReportRequest(): OrderReportRequest {
		val orderListRequest = OrderReportRequest()

		val strSrvDateStart =
			DateTimeUtils.toDateTime(getTodayBeginInMillis(), DateTimeUtils.SERVER_DATE_PATTERN)

		orderListRequest.RentDate = strSrvDateStart

		return orderListRequest
	}

	fun prepareDefaultTicketsRequest(): OrderListRequest {
		val orderListRequest = OrderListRequest()
		val timeZoneUTC = TimeZone.UTC
		val sofiaTimeZone = TimeZone.of("Europe/Sofia")
		val currentTimeUTC = Clock.System.now()

		val offset = sofiaTimeZone.offsetAt(currentTimeUTC)
		val dateTimeInstantMSK: Instant =
			currentTimeUTC.plus(offset.totalSeconds, DateTimeUnit.SECOND)

		val todayDateTimeMSK: LocalDateTime = currentTimeUTC.toLocalDateTime(sofiaTimeZone)
		val threeDaysAgoInstant = dateTimeInstantMSK.minus(3.days)

		val startDateTime = threeDaysAgoInstant.toLocalDateTime(timeZoneUTC)
		val startOfPeriod = LocalDateTime(
			year = startDateTime.year,
			month = startDateTime.month,
			day = startDateTime.day,
			hour = 0,
			minute = 0,
			second = 0
		                                 )

		val endOfPeriod = LocalDateTime(
			year = todayDateTimeMSK.year,
			month = todayDateTimeMSK.month,
			day = todayDateTimeMSK.day,
			hour = 23,
			minute = 59,
			second = 59
		                               )

		val startMillis = startOfPeriod.toInstant(timeZoneUTC).toEpochMilliseconds() // to instant with no offset
		val endMillis = endOfPeriod.toInstant(timeZoneUTC).toEpochMilliseconds() // to instant with no offset

		orderListRequest.CreateDateFrom = DateTimeUtils.toServerDate(startMillis)
		orderListRequest.CreateDateTo = DateTimeUtils.toServerDate(endMillis)
		orderListRequest.State = "RD" // Created

		return orderListRequest // last 3 days from start of the 3 days ago day till end of the current day
	}

	fun prepareDefaultOrdersRequest(thisUserOnly: Boolean): OrderListRequest {
		val orderListRequest = OrderListRequest()
		val timeZoneUTC = TimeZone.UTC
		val sofiaTimeZone = TimeZone.of("Europe/Sofia")
		val currentTimeUTC = Clock.System.now()

		val todayDateTime: LocalDateTime = currentTimeUTC.toLocalDateTime(sofiaTimeZone)
		val oneDayAgoInstant = currentTimeUTC.minus(1.days)

		val startDateTime = oneDayAgoInstant.toLocalDateTime(sofiaTimeZone)

		val startMillis = startDateTime.toInstant(timeZoneUTC).toEpochMilliseconds()
		val endMillis = todayDateTime.toInstant(timeZoneUTC).toEpochMilliseconds()

		orderListRequest.CreateDateFrom = DateTimeUtils.toServerDateTime(startMillis)
		orderListRequest.CreateDateTo = DateTimeUtils.toServerDateTime(endMillis)

		if (thisUserOnly) {
			orderListRequest.UserId = SessionVars.userSession.UserId
		}
		return orderListRequest // last 24 hours period
	}
}