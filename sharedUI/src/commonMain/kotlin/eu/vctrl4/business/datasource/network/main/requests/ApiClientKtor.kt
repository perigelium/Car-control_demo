package eu.vctrl4.business.datasource.network.main.requests

import eu.vctrl4.business.constants.*
import eu.vctrl4.presentation.utils.*
import kotlin.uuid.*

object ApiClientKtor {
	@OptIn(ExperimentalUuidApi::class)
	inline fun <reified T> buildApiRequestBody(
		type: String,
		requestBody: T?,
		template: String? = null
): ApiRequestBodyKtor<T>? {
		try {
			val dt =
				DateTimeUtils.getCurrentSofiaTimeAsString(DateTimeUtils.SERVER_DATE_TIME_PATTERN_LONG)
					?: ""
			val mn = Uuid.random().toString()

			val sign = AppJson.encodeToString(requestBody)

			val apiRequestBody = ApiRequestBodyKtor<T>(
				dt,
				Constants.BACK_END_SUBSCRIBER,
				mn,
				type,
				requestBody,
				sign
			                                          )
			template?.apply { apiRequestBody.template = this }

			return apiRequestBody
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return null
	}
}