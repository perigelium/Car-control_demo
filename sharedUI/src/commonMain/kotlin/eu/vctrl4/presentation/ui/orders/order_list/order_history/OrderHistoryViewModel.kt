package eu.vctrl4.ui.orders.order_list.order_history

import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.UIComponent.DialogMsg
import eu.vctrl4.business.core.UIComponent.Toast
import eu.vctrl4.business.datasource.network.common.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*


class OrderHistoryViewModel(private val orderHistoryUseCase: OrderHistoryUseCase, )
    : BaseViewModel<OrderHistoryEvent, OrderHistoryViewState, Nothing>()
{
    override fun setInitialState() = OrderHistoryViewState()

    override fun onTriggerEvent(event: OrderHistoryEvent)
    {
        when (event)
        {
            is OrderHistoryEvent.OnUpdateNetworkState -> {
                if(event.networkState == NetworkState.Failed) display { Toast(Res.string.error_check_internet_connection.asState) }
            }

            is OrderHistoryEvent.RequestOrderHistory ->
            {
                requestOrderHistory(event.orderId)
            }
        }
    }

    private fun requestOrderHistory(orderId:String)
    {
        executeUseCase(orderHistoryUseCase.execute(params = orderId),
            onSuccess = { resp ->
                if(resp?.isNotEmpty() == true)
                {
                    setState { copy(items = resp) }
                }
                else
                {
                    display { DialogMsg(JAlertResponse(Res.string.error_no_order_history.asState)) }
                }
            }, onLoading = {
                setState { copy(progressBarState = it) }
            }, onNetworkStatus = {
                setEvent(OrderHistoryEvent.OnUpdateNetworkState(it))
            })
    }
}
