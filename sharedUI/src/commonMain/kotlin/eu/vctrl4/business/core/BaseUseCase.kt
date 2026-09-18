package eu.vctrl4.business.core


import eu.vctrl4.*
import eu.vctrl4.business.constants.Constants.STATUS_OK
import eu.vctrl4.business.datasource.network.common.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.serialization.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.io.*

abstract class BaseUseCase<Params, ApiResponse, Result>(prefsStoreManager: PrefsStoreManagerImpl? = null)
{
    abstract suspend fun callRepo(params: Params): MainGenericResponse<ApiResponse>?

    abstract fun convertApiResponse(apiResponse: MainGenericResponse<ApiResponse>?): Result?

    abstract val progressBarState: ProgressBarState // Default progress bar type
    abstract val needNetworkState: Boolean  // Whether to emit network states
    abstract val showAlert: Boolean // Check if we need to show error

    fun execute(params: Params): Flow<DataState<Result>> = flow {
        try
        {
            emit(DataState.Loading(progressBarState))

            val result = callRepo(params)

            if (showAlert)
            {
                result?.alert?.let { alert ->
                    emit(DataState.Response(uiComponent = UIComponent.Toast(message = alert.message)))
                }
            }
            emit(DataState.Data(data = convertApiResponse(result), status = result?.statusCode == STATUS_OK))
        } catch (e: JsonConvertException)
        {
            if (BuildKonfig.DEBUG)
            {
                emit(DataState.Response(UIComponent.DialogTitleText("JsonConvertException !", e.message.toString())))
            }
            e.printStackTrace()
        }
        catch (e: CancellationException) {
	        // CRITICAL: Let the coroutine framework handle cancellation naturally. Do not log or wrap this.
	        throw e
        }
        catch (e: ResponseException) {
	        // Handles 4xx and 5xx responses passing through Ktor's RedirectResponse/ClientRequestException
	        if (BuildKonfig.DEBUG) {
		        emit(DataState.Response(UIComponent.DialogTitleText("Server Error (${e.response.status.value})", e.message.toString())))
	        }
        }
        catch (e: kotlinx.io.IOException)
        {
            emit(DataState.NetworkStatus(NetworkState.Failed))
            e.printStackTrace()
            if (BuildKonfig.DEBUG)
            {
                emit(DataState.Response(UIComponent.DialogTitleText("Network Error", e.message.toString())))
            }
        } catch (e: HttpRequestTimeoutException)
        {
            val strMsg = if (BuildKonfig.DEBUG) e.message.toString() else "Request timed out"
            emit(DataState.Response(UIComponent.Toast(strMsg)))
        } catch (e: ConnectTimeoutException)
        {
            emit(DataState.Response(UIComponent.Toast("Server is overloaded with requests\ntry later")))
        } catch (e: IOException)
        {
            e.printStackTrace()
            if (BuildKonfig.DEBUG)
            {
                emit(DataState.Response(UIComponent.DialogTitleText("IOException !", e.message.toString())))
            }
        } catch (e: Exception)
        {
            if (BuildKonfig.DEBUG)
            {
                emit(DataState.Response(UIComponent.DialogTitleText("Unexpected Error", e.message.toString())))
            }
            e.printStackTrace()
        } finally
        {
            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }
}
