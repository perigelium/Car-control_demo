package eu.vctrl4.business.datasource.network.main

import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.datasource.network.main.requests.*
import eu.vctrl4.business.datasource.network.main.responses.*
import eu.vctrl4.business.datasource.storage.entities.*


interface MainService {
    companion object {

        const val urlControl = "control"
	    const val urlConfig =  "System.GetConfig"
	    const val urlAuth = "Membership.Auth"
	    const val urlOrdersCount = "Orders.Count"
	    const val urlOrdersList = "Orders.List"
	    const val urlOrderDetails = "Orders.GetOrders"
	    const val urlDeleteFirebaseToken = "Membership.UserTokenMobileDelete"
	    const val urlOrderReports = "Reports.VehicleByDate"
        const val urlGetCompanies = "Dictionary.GetCompanies"
        const val urlGetVehicleTypes = "Dictionary.GetVehicleTypes"
	    const val urlGetOrdersNumbersAndDates = "Orders.GetOrderNumberByIds"
        const val urlGetVehicleOptions = "Dictionary.GetVehicleOptionTypes"
        const val urlGetVehicleCapacityClasses = "Dictionary.GetCapacityClass"
        const val urlCancelOrder = "Orders.CancelOrder"
        const val urlGetVehiclePositionPrefix = "api-nest/wialon/position?deviceId="
        const val urlGetVehicleTrackPrefix = "api-nest/wialon/track?deviceId="
    }

    // Dictionaries
    suspend fun requestVehicleCapacityClasses(): MainGenericResponse<List<VehicleCapacityClass>?>
    suspend fun requestVehicleOptions(): MainGenericResponse<List<VehicleOption>?>
    suspend fun requestVehicleTypes(): MainGenericResponse<List<VehicleType>?>
    suspend fun requestCompanies(request: UserIdObj): MainGenericResponse<List<Company>?>

    // Authentication
    suspend fun requestConfig(): MainGenericResponse<List<Config?>?>
    suspend fun auth(apiRequestBodyKtor: ApiRequestBodyKtor<AuthRequest>): MainGenericResponse<Session?>

    // Logout
    suspend fun deleteFirebaseToken(request: TokenObj?): MainGenericResponse<WDError?>?

    // Online board
    suspend fun requestBoardOrderList(request: OrderReportRequest?): MainGenericResponse<List<BoardOrder>?>
    suspend fun requestVehiclePosition(request: VehiclePositionRequest): MainGenericResponse<VehiclePosition?>
    suspend fun requestVehicleTrack(request: VehicleTrackRequest):MainGenericResponse<List<VehicleTrackPoint>?>

    // Orders
    suspend fun cancelOrder(request: CancelOrderRequest?): MainGenericResponse<WDError?>?
    suspend fun requestOrderList(request: OrderListRequest?): MainGenericResponse<List<Order>?>
    suspend fun requestOrdersCount(request: OrderListRequest?): MainGenericResponse<OrdersCount?>
    suspend fun requestOrderByOrderId(orderId:String): MainGenericResponse<List<Order>?>
    suspend fun requestOrdersWithDetails(request: OrderRequest): MainGenericResponse<List<OrderDetails>?>

}