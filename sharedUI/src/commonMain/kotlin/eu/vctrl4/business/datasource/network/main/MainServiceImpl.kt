package eu.vctrl4.business.datasource.network.main

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.STATUS_OK
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlCancelOrder
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlConfig
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlDeleteFirebaseToken
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetCompanies
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehicleCapacityClasses
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehicleOptions
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehiclePositionPrefix
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehicleTrackPrefix
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehicleTypes
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlOrderDetails
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlOrderReports
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlOrdersCount
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlOrdersList
import eu.vctrl4.business.datasource.network.main.requests.*
import eu.vctrl4.business.datasource.network.main.responses.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.utils.*
import eu.vctrl4.common.*
import eu.vctrl4.storage.entities.*
import eu.vctrl4.storage.remote.entities.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


class MainServiceImpl(
	private val httpClient: HttpClient) : MainService {

	override suspend fun auth(apiRequestBodyKtor: ApiRequestBodyKtor<AuthRequest>): MainGenericResponse<Session?> {
		val httpResponse = httpClient.post {
			url {
				takeFrom(BuildKonfig.BASE_URL)
				encodedPath += MainService.urlControl
			} //val requestBody = ApiClientKtorEmptyBody.buildApiRequestBody("Membership.Auth")

			if (BuildKonfig.DEBUG) {
				val strRequest = AppJson.encodeToString(apiRequestBodyKtor)
				Log.d(BuildKonfig.APP_LOG_TAG, strRequest)
			}
			setBody(apiRequestBodyKtor)

			contentType(ContentType.Application.Json)
		}

		val httpStatusValue =
			if (httpResponse.status.value in 200..299) STATUS_OK else httpResponse.status.value
		val strBody = httpResponse.bodyAsText()
		val alert = if (httpStatusValue == STATUS_OK) null else JAlertResponse(
			title = Res.string.error_data_access.asState, message = httpResponse.status.description
		                                                                      )
		val wdResponse: WDResponse<Session?>? = httpResponse.body()

		val response = MainGenericResponse<Session?>(
			result = if (httpStatusValue == STATUS_OK && strBody.isNotEmpty()) wdResponse?.body else httpResponse.body(),
			statusCode = httpStatusValue,
			alert = alert
		                                            )
		return response
	}

	private suspend inline fun <reified Req, reified Resp> executePostRequest(
		requestBody: Req): MainGenericResponse<Resp> {

		val httpResponse = httpClient.post {
			url {
				takeFrom(BuildKonfig.BASE_URL)
				encodedPath += MainService.urlControl
			}

			if (BuildKonfig.DEBUG) {
				val strRequest = AppJson.encodeToString(requestBody)
				Log.d(BuildKonfig.APP_LOG_TAG, strRequest)
			}

			setBody(requestBody)
			contentType(ContentType.Application.Json)
		}

		val httpStatusValue = if (httpResponse.status.value in 200..299) STATUS_OK else httpResponse.status.value
		val strBody = httpResponse.bodyAsText()

		val alert = if (httpStatusValue == STATUS_OK) null else JAlertResponse(
			title = "Ktor response error:",
			message = httpResponse.status.description)

		val wdResponse: WDResponse<Resp> = httpResponse.body()

		return MainGenericResponse(
			result = if (httpStatusValue == STATUS_OK && strBody.isNotEmpty()) wdResponse.body else httpResponse.body(),
			statusCode = httpStatusValue,
			alert = alert
		                          )
	}

	override suspend fun requestConfig(): MainGenericResponse<List<Config?>?> {
		val requestBody = ApiClientKtorEmptyBody.buildApiRequestBody(urlConfig)
		return executePostRequest<ApiRequestEmptyBodyKtor?, List<Config?>?>(requestBody)
	}

	override suspend fun requestOrderList(request: OrderListRequest?): MainGenericResponse<List<Order>?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlOrdersList, request, null)
		return executePostRequest<ApiRequestBodyKtor<OrderListRequest>?, List<Order>?>(requestBody)
	}

	override suspend fun requestOrdersWithDetails(request: OrderRequest): MainGenericResponse<List<OrderDetails>?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlOrderDetails, request, null)
		return executePostRequest<ApiRequestBodyKtor<OrderRequest>?, List<OrderDetails>?>(requestBody)
	}

	override suspend fun requestOrdersCount(request: OrderListRequest?): MainGenericResponse<OrdersCount?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlOrdersCount, request, null)
		return executePostRequest<ApiRequestBodyKtor<OrderListRequest>?, OrdersCount?>(requestBody)
	}

	override suspend fun cancelOrder(request: CancelOrderRequest?): MainGenericResponse<WDError?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlCancelOrder, request, null)
		return executePostRequest<ApiRequestBodyKtor<CancelOrderRequest>?, WDError?>(requestBody)
	}

	override suspend fun requestVehicleCapacityClasses(): MainGenericResponse<List<VehicleCapacityClass>?> {
		val requestBody = ApiClientKtorEmptyBody.buildApiRequestBody(urlGetVehicleCapacityClasses)
		return executePostRequest<ApiRequestEmptyBodyKtor?, List<VehicleCapacityClass>?>(requestBody)
	}

	override suspend fun requestVehicleOptions(): MainGenericResponse<List<VehicleOption>?> {
		val requestBody = ApiClientKtorEmptyBody.buildApiRequestBody(urlGetVehicleOptions)
		return executePostRequest<ApiRequestEmptyBodyKtor?, List<VehicleOption>?>(requestBody)
	}

	override suspend fun requestVehicleTypes(): MainGenericResponse<List<VehicleType>?> {
		val requestBody = ApiClientKtorEmptyBody.buildApiRequestBody(urlGetVehicleTypes)
		return executePostRequest<ApiRequestEmptyBodyKtor?, List<VehicleType>?>(requestBody)
	}

	override suspend fun deleteFirebaseToken(request: TokenObj?): MainGenericResponse<WDError?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlDeleteFirebaseToken, request, null)
		return executePostRequest<ApiRequestBodyKtor<TokenObj>?, WDError?>(requestBody)
	}

	override suspend fun requestCompanies(request: UserIdObj): MainGenericResponse<List<Company>?> {
		val requestBody = ApiClientKtor.buildApiRequestBody<UserIdObj>(urlGetCompanies, request)
		return executePostRequest<ApiRequestBodyKtor<UserIdObj>?, List<Company>?>(requestBody)
	}

	override suspend fun requestOrderByOrderId(orderId: String): MainGenericResponse<List<Order>?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlOrderDetails, orderId, null)
		return executePostRequest<ApiRequestBodyKtor<String>?, List<Order>?>(requestBody)
	}

	override suspend fun requestBoardOrderList(request: OrderReportRequest?): MainGenericResponse<List<BoardOrder>?> {
		val requestBody = ApiClientKtor.buildApiRequestBody(urlOrderReports, request, null)
		return executePostRequest<ApiRequestBodyKtor<OrderReportRequest>?, List<BoardOrder>?>(requestBody)
	}

	override suspend fun requestVehiclePosition(request: VehiclePositionRequest): MainGenericResponse<VehiclePosition?> {
		val httpResponse = httpClient.get {
			url {
				takeFrom(BuildKonfig.API_CAR_CONTROL_URL)
				encodedPath += urlGetVehiclePositionPrefix
				encodedPath += "${request.deviceId}&token=${request.token}&url=${request.url}"
			}
			contentType(ContentType.Application.Json)
		}

		val httpStatusValue =
			if (httpResponse.status.value in 200..299) STATUS_OK else httpResponse.status.value
		val strBody = httpResponse.bodyAsText()
		val alert = if (httpStatusValue == STATUS_OK) null else JAlertResponse(
			title = Res.string.error_data_access.asState, message = httpResponse.status.description
		                                                                      )
		return MainGenericResponse(
			result = if (httpStatusValue == STATUS_OK && strBody.isNotEmpty()) httpResponse.body() else null,
			statusCode = httpStatusValue,
			alert = alert
		                          )
	}

	override suspend fun requestVehicleTrack(request: VehicleTrackRequest): MainGenericResponse<List<VehicleTrackPoint>?> {
		val httpResponse = httpClient.get {
			url {
				takeFrom(BuildKonfig.API_CAR_CONTROL_URL)
				encodedPath += urlGetVehicleTrackPrefix
				encodedPath += "${request.deviceId}&token=${request.token}&timeFrom=${request.timeFrom}&timeTo=${request.timeTo}&url=${request.url}"
			}
			contentType(ContentType.Application.Json)
		}

		val httpStatusValue =
			if (httpResponse.status.value in 200..299) STATUS_OK else httpResponse.status.value
		val strBody = httpResponse.bodyAsText()
		val alert = if (httpStatusValue == STATUS_OK) null else JAlertResponse(
			title = Res.string.error_data_access.asState, message = httpResponse.status.description
		                                                                      )
		return MainGenericResponse(
			result = if (httpStatusValue == STATUS_OK && strBody.isNotEmpty()) httpResponse.body() else null,
			statusCode = httpStatusValue,
			alert = alert
		                          )
	}
}