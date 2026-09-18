package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.remote.entities.*

class OrderDetailsUseCase(private val service: MainService): BaseUseCase<String, List<OrderDetails>?, OrderDetails?>()
{
    override suspend fun callRepo(params: String): MainGenericResponse<List<OrderDetails>?>
    {
        val request = OrderRequest(arrayListOf(params), true)
        return service.requestOrdersWithDetails(request)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<List<OrderDetails>?>?): OrderDetails?
    {
        val result =  apiResponse?.result

        val orderDetails = result?.firstOrNull()

        return  orderDetails
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true
}