package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.network.main.requests.OrderRequest
import eu.vctrl4.business.datasource.storage.entities.*

class OrderHistoryUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl): BaseUseCase<String, List<OrderDetails>?, List<OrderHistoryItem>?>(prefsStoreManager)
{
    override suspend fun callRepo(params: String): MainGenericResponse<List<OrderDetails>?>
    {
        val request = OrderRequest(arrayListOf(params), true)
        return service.requestOrdersWithDetails(request)
    }

    override fun convertApiResponse(apiResponse: MainGenericResponse<List<OrderDetails>?>?): List<OrderHistoryItem>?
    {
        val result =  apiResponse?.result

        var mOrderHistoryList = result?.first()?.History ?: listOf()

        if (mOrderHistoryList.isNotEmpty())
        {
            mOrderHistoryList = mOrderHistoryList.sortedBy { it.Date }
        }
        return  mOrderHistoryList
    }

    override val progressBarState: ProgressBarState = ProgressBarState.Loading
    override val needNetworkState: Boolean = true
    override val showAlert: Boolean = true

/*    @JvmStatic
    fun getOrderHistory(orderId:String)
    {
        val runnable = Runnable {

            val request = OrderRequest(arrayListOf(orderId), true)

            val resp = ObjListRepoWD.instance!!.requestObjList<Order>(
                "Orders.GetOrders", object : TypeToken<eu.vctrl4.storage.remote.entities.WDResponse<List<Order>?>?>()
                {}.type, request
            )

            postValue(resp)
        }
        Executors.newSingleThreadExecutor().execute(runnable)
    }*/
}