package eu.vctrl4.presentation.ui.orders.order_details


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.navigation.*
import eu.vctrl4.presentation.ui.orders.order_details.view_model.*
import eu.vctrl4.ui.custom_views.composable.*
import eu.vctrl4.ui.orders.*
import eu.vctrl4.ui.orders.order_details.view_model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@Composable
fun OrderDetailsNav(viewModel: OrderDetailsViewModel, strAction:String?, popUp: (Boolean) -> Unit)
{
    val navigator = rememberNavController()

    fun navigate(route: String)
    {
        when (route)
        {
            "nav_supplier" -> viewModel.prepareSupplierData()
            "nav_customer" -> viewModel.prepareCustomerData()
            "nav_supply" -> viewModel.prepareSupplyData()
            "nav_transportation" -> viewModel.prepareTransportationData()
            "nav_additional" -> viewModel.prepareAdditionalData()
        }
    }

    fun copyOrder(order: OrderDetails)
    {
        order.Id = null
        order.OrderId = null
        order.Number = null
    }

    fun performAction(strAction: String, order: OrderDetails)
    {
        when (strAction)
        {
            "copy" -> copyOrder(order)
            "edit" ->
            {
                //openEditOrder()
            }

            "delete" -> {
	            viewModel.state.value.orderDetails.Id?.let {
		            navigator.navigate(OrderDetailsNavigation.CancelOrder(it))
	            }
            }
        }
    }

    NavHost(
        startDestination = OrderDetailsNavigation.OrderDetails, navController = navigator, modifier = Modifier.fillMaxSize()
    ) {
        composable<OrderDetailsNavigation.OrderDetails> {

            DefaultScreenWrap(
                errors = viewModel.errors, progressBarState = viewModel.state.value.progressBarState, screenContent = {

		            OrderDetailsScreen(
			            viewModel.state.value.orderDetails,
			            viewModel.state.value.titleTexts,
			            viewModel.state.value.titleTextsTranspTop,
			            viewModel.state.value.titleTextsTranspOptionTypes,
			            viewModel.state.value.titleTextsTranspBottom,
			            { popUp(false) },
			            { strAction ->
					            performAction(strAction, viewModel.state.value.orderDetails)
			            },
			            navigate = { navigate(it) })
                })

            LaunchedEffect(strAction) {
                delay(500L)
                strAction?.let { performAction(strAction, viewModel.state.value.orderDetails) }
            }
        }

        composable<OrderDetailsNavigation.CancelOrder> {

            CancelOrderDialog(
	            title = Res.string.are_you_sure_you_want_to_cancel_your_order.asState,
	            onDismiss = { navigator.popBackStack() },
	            onSubmit = {
                    viewModel.onTriggerEvent(OrderDetailsEvent.CancelOrder(it))
                })

            LaunchedEffect(Unit) {
                delay(500L)
                viewModel.action.onEach { effect ->
                    when (effect) {
                        is OrderDetailsAction.Popup -> {
	                        navigator.popBackStack()
                            popUp(true)
                        }

                        else ->
                        {
                        }
                    }
                }.collect {}
            }
        }

    }
}
