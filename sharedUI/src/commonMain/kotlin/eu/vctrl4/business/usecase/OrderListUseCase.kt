package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.entities.Order
import eu.vctrl4.business.datasource.storage.entities.OrderListRequest

class OrderListUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl): BaseUseCase<OrderListRequest?, List<Order>?, List<Order>?>(prefsStoreManager)
{
	override suspend fun callRepo(params: OrderListRequest?): MainGenericResponse<List<Order>?>
	{
		return service.requestOrderList(params)
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<Order>?>?): List<Order>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true


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