package eu.vctrl4.business.core

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.IS_DEMO_MODE
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.MainService.Companion.urlGetVehicleTrackPrefix
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.observer.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*


@OptIn(ExperimentalSerializationApi::class)
fun ktorHttpClient() = if (IS_DEMO_MODE) {
	HttpClient(createMockEngine()) {
		configureClient()
	}
} else {
	HttpClient {
		configureClient()
	}
}

@OptIn(ExperimentalSerializationApi::class)
private fun HttpClientConfig<*>.configureClient() {
	expectSuccess = false

	install(HttpTimeout) {
		connectTimeoutMillis = 10000L
		requestTimeoutMillis = 30000L
		socketTimeoutMillis = 30000L
	}

	install(ResponseObserver) {
		onResponse { response ->
			println("AppDebug HTTP ResponseObserver status: ${response.status.value}")
		}
	}

	HttpResponseValidator {
		validateResponse { response: HttpResponse ->
			val statusCode = response.status.value
			if (statusCode == 401) {				// srvQueryResultManager.onTriggerEvent(SrvQueryResultEvent.Unauthorized)
			}
		}
	}

	install(Logging) {
		level = LogLevel.ALL
		logger = object : Logger {
			override fun log(message: String) {
				println("AppDebug KtorHttpClient message:$message")
			}
		}
	}

	install(ContentNegotiation) {
		json(AppJson)
	}
}

private fun createMockEngine() = MockEngine { request ->
	val path = request.url.encodedPath
	val responseHeaders = headersOf(HttpHeaders.ContentType, "application/json")

	suspend fun readMockFile(filePath: String): String {
		return Res.readBytes(filePath)
			.decodeToString()
			.removePrefix("\uFEFF") // remove UTF-8 prefix
			.trim()
	}

	if (path.contains(MainService.urlControl)) {
		val requestBodyText = request.body.toText()

		val apiMethod = try {
			val jsonElement = AppJson.parseToJsonElement(requestBodyText)
			jsonElement.jsonObject["type"]?.jsonPrimitive?.content ?: ""
		} catch (e: Exception) {
			""
		}

		return@MockEngine when (apiMethod) {
			"System.GetConfig" -> {
				respond(content = readMockFile("files/json/config.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Membership.Auth" -> {
				respond(content = readMockFile("files/json/session.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Dictionary.GetCompanies" -> {
				respond(content = readMockFile("files/json/companies.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Orders.Count" -> {
				respond(content = readMockFile("files/json/ordersCount.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Orders.List" -> {
				respond(content = readMockFile("files/json/orders.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Orders.GetOrders" -> {
				respond(content = readMockFile("files/json/orderDetails.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Reports.VehicleByDate" -> {
				respond(content = readMockFile("files/json/boardOrders.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}

			"Orders.CancelOrder" -> {
				respond(content = readMockFile("files/json/cancelOrder.json"), status = HttpStatusCode.OK, headers = responseHeaders)
			}





			else -> {
				respond(
					content = """{"error": "Method $apiMethod not mocked"}""",
					status = HttpStatusCode.NotFound,
					headers = responseHeaders
				       )
			}
		}
	} else {

		if(request.url.toString().contains(urlGetVehicleTrackPrefix))
		{
			respond(content = readMockFile("files/json/vehicleTrack.json"), status = HttpStatusCode.OK, headers = responseHeaders)
		}
		else {
			respond(
				content = """{"error": "Path not found"}""",
				status = HttpStatusCode.NotFound,
				headers = responseHeaders
			       )
		}
	}
}


suspend fun readMockFile(filePath: String): String {
	return Res.readBytes(filePath)
		.decodeToString()
		.removePrefix("\uFEFF")
		.trim()
}

// Reading text from the OutgoingContent
private suspend fun OutgoingContent.toText(): String {
	return when (this) {
		is OutgoingContent.ByteArrayContent -> this.bytes().decodeToString()
		is OutgoingContent.ReadChannelContent -> this.readFrom().readRemaining().readText()
		else -> ""
	}
}

object KtorHttpClientOld {
	@OptIn(ExperimentalSerializationApi::class)
	fun httpClient() = HttpClient {
		expectSuccess = false
		install(HttpTimeout) {
			connectTimeoutMillis = 10000L
			requestTimeoutMillis = 30000L
			socketTimeoutMillis = 30000L
		}

		install(ResponseObserver) {
			onResponse { response ->
				println("AppDebug HTTP ResponseObserver status: ${response.status.value}")
			}
		}

		HttpResponseValidator {
			validateResponse { response: HttpResponse ->
				val statusCode = response.status.value

				if (statusCode == 401) { //srvQueryResultManager.onTriggerEvent(SrvQueryResultEvent.Unauthorized)
				}

				when (statusCode) { //in 300..399 -> throw RedirectResponseException(response)
					//in 400..499 -> throw ClientRequestException(response)
					//throw ServerResponseException(response)
				}

				/*                                    if (statusCode >= 600) {
														throw ResponseException(response)
													}*/ //}

				/*                                handleResponseException { cause: Throwable ->
													throw cause
												}*/
			}
		}


		install(Logging) { //  logger = Logger.DEFAULT
			level = LogLevel.ALL

			logger = object : Logger {
				override fun log(message: String) {
					println("AppDebug KtorHttpClient message:$message")
				}
			}
		}
		install(ContentNegotiation) {
			json(AppJson)

			/*            (Json {
							explicitNulls = false
							ignoreUnknownKeys = true
							//isLenient = true
							//prettyPrint = true
							coerceInputValues = true
							encodeDefaults = true
							classDiscriminator = "#class"
						})*/

			/*            Gson gson = new GsonBuilder()
							.registerTypeAdapter(Id.class, new IdTypeAdapter())
							.enableComplexMapKeySerialization()
							.serializeNulls()
							.setDateFormat(DateFormat.LONG, DateFormat.LONG)
							.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
							.setPrettyPrinting()
							.setVersion(1.0)
							.create();*/
		}

		/*        install(HttpRequestRetry) {
					maxRetries = 0
					constantDelay(millis = 3000L)
					exponentialDelay() // Increases wait time between retries
				}*/
	}
}
