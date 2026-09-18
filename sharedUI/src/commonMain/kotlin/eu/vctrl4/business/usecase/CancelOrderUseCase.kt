package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.requests.CancelOrderRequest
import eu.vctrl4.business.datasource.network.main.responses.*

class CancelOrderUseCase(private val service: MainService) :
    BaseUseCase<CancelOrderRequest?, WDError?, WDError?>()
{
    override suspend fun callRepo(params: CancelOrderRequest?): MainGenericResponse<WDError?>?
    {
        return service.cancelOrder(params)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<WDError?>?): WDError?
    {
        return apiResponse?.result
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}