package eu.vctrl4.business.datasource.network.main.requests

import eu.vctrl4.business.constants.*
import eu.vctrl4.presentation.utils.*
import kotlin.uuid.*

object ApiClientKtorEmptyBody
{
    @OptIn(ExperimentalUuidApi::class)
    fun buildApiRequestBody(type: String, template: String? = null): ApiRequestEmptyBodyKtor?
    {
        try
        {
            val dt = DateTimeUtils.getCurrentSofiaTimeAsString(DateTimeUtils.SERVER_DATE_TIME_PATTERN_LONG)?:""
            val mn = Uuid.random().toString()

            val sign = ""

            val apiRequestBody = ApiRequestEmptyBodyKtor(dt, Constants.BACK_END_SUBSCRIBER, mn, type, sign)
            template?.apply { apiRequestBody.template = this }

            return apiRequestBody
        } catch (e: Exception)
        {
            e.printStackTrace()
        }
        return null
    }
}