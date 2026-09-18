package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.storage.entities.*
import eu.vctrl4.storage.remote.entities.*

class OrdersCountUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl): BaseUseCase<OrderListRequest?, OrdersCount?, Int?>(prefsStoreManager)
{
	override suspend fun callRepo(params: OrderListRequest?): MainGenericResponse<OrdersCount?>
	{
		return service.requestOrdersCount(params)
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<OrdersCount?>?): Int?
	{
		return apiResponse?.result?.Count
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = false


/*	suspend fun requestOrdersAsync(orderReportRequest: OrderListRequest?): Pair<List<Order>?,String?>?
	{
		val coroutineName = object
		{}.javaClass.enclosingMethod?.name ?: ""

		val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName(coroutineName))
		val deferred = scope.async {
			return@async getOrders(orderReportRequest)
		}
		return deferred.await()
	}

	fun getOrders(request: OrderListRequest?): Pair<List<Order>?, String?>
    {
			val ordersResp = ObjListRepoWD.instance!!.requestObjList<Order>(
				"Reports.VehicleByDate",
				object : TypeToken<WDResponse<List<Order>?>?>()
				{}.type,
				request
			)
		return ordersResp
	}*/
}