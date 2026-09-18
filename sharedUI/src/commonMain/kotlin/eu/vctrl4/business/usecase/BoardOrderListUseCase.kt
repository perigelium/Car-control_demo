package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.storage.remote.entities.*

class BoardOrderListUseCase(private val service: MainService, private val prefsStoreManager: PrefsStoreManagerImpl): BaseUseCase<OrderReportRequest?, List<BoardOrder>?, List<BoardOrder>?>(prefsStoreManager)
{
	override suspend fun callRepo(params: OrderReportRequest?): MainGenericResponse<List<BoardOrder>?>
	{
		return service.requestBoardOrderList(params)
	}

	override fun convertApiResponse(apiResponse: MainGenericResponse<List<BoardOrder>?>?): List<BoardOrder>?
	{
		return apiResponse?.result
	}

	override val progressBarState: ProgressBarState = ProgressBarState.Loading
	override val needNetworkState: Boolean = true
	override val showAlert: Boolean = true


/*	suspend fun requestBoardOrdersAsync(orderReportRequest: OrderReportRequest?): Pair<List<BoardOrder>?,String?>?
	{
		val coroutineName = object
		{}.javaClass.enclosingMethod?.name ?: ""

		val scope = CoroutineScope(Job() + Dispatchers.IO + CoroutineName(coroutineName))
		val deferred = scope.async {
			return@async getOrders(orderReportRequest)
		}
		return deferred.await()
	}

	fun getOrders(request: OrderReportRequest?): Pair<List<BoardOrder>?, String?>
    {
			val ordersResp = ObjListRepoWD.instance!!.requestObjList<BoardOrder>(
				"Reports.VehicleByDate",
				object : TypeToken<eu.vctrl4.storage.remote.entities.WDResponse<List<BoardOrder>?>?>()
				{}.type,
				request
			)
		return ordersResp
	}*/
}