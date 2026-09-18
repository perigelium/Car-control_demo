package eu.vctrl4.presentation.ui.tickets


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.navigation.*
import androidx.navigation.compose.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.navigation.*
import eu.vctrl4.presentation.ui.*
import eu.vctrl4.presentation.ui.base.*
import eu.vctrl4.presentation.ui.tickets.ticket_confirmation.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.filter.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.filter.view_model.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.view_model.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.presentation.utils.DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT
import eu.vctrl4.presentation.utils.DateTimeUtils.UI_DATE_PATTERN_SHORT
import eu.vctrl4.theme.Colors.cl_69BE28
import eu.vctrl4.theme.Colors.cl_e02020
import eu.vctrl4.ui.tickets.ticket_confirmation.*
import org.koin.compose.*

@Composable
fun TicketsNav(viewModel: TicketListViewModel, onMenuBtnClick: () -> Unit) {
	val navigator = rememberNavController()

	NavHost(
		startDestination = TicketsNavigation.OrderList,
		navController = navigator,
		modifier = Modifier.fillMaxSize()
	       ) {
		composable<TicketsNavigation.OrderList> {

			DefaultScreenWrap(
				errors = viewModel.errors,
				progressBarState = viewModel.state.value.progressBarState,
				screenContent = {
					TicketListScreen(
						state = viewModel.state.value,
						onFilterClick = {
							navigator.navigate(TicketsNavigation.OrdersFilter)
						},
						onSearchClick = {
							viewModel.onTriggerEvent(
								TicketListEvent.SearchOrderInvoked(it)
							                        )
						},
						events = { viewModel.onTriggerEvent(it) },
						topAppBar = {
							TopAppBarNavDrawer(
								title = Res.string.tickets.asState,
								itemsTotalLine = redrawTicketsCountTextView(
									mTicketsTotal = viewModel.state.value.items.size
								                                           ),
								onMenuBtnClick = onMenuBtnClick
							                  )
						},
						onScrolledToEnd = { viewModel.onTriggerEvent(TicketListEvent.OnScrolledToEnd) },
						onTicketConfirmClicked = {
							if(!it.Ticket?.Number.isNullOrEmpty()) {
								val strOrder = AppJson.encodeToString(it)
								navigator.navigate(
									TicketsNavigation.TicketConfirmationScreen(
										strOrder
									                                          )
								                  )
							}
						},)
				},)
		}

		composable<TicketsNavigation.OrdersFilter> {
			val filterViewModel: TicketsFilterViewModel = koinInject()

			LaunchedEffect(Unit) {
				filterViewModel.onTriggerEvent(TicketsFilterViewEvent.OnFilterInvoked)
			}

			TicketsFiltersDialog(
				companies = filterViewModel.state.value.custCompanies,
				ordersRequest = viewModel.state.value.ordersRequest,
				onSubmit = {
					viewModel.onTriggerEvent(TicketListEvent.FilterDialogSubmitted(it))
					navigator.popBackStack()
				},
				onDismiss = { navigator.popBackStack() },
			                    )
		}

		composable<TicketsNavigation.TicketConfirmationScreen> { backStackEntry ->

			val argument = backStackEntry.toRoute<TicketsNavigation.TicketConfirmationScreen>()
			val order = AppJson.decodeFromString<Order>(argument.strOrder)

			val showButtons = remember { mutableStateOf(true) }

			TicketConfirmationScreen(
				order = order,
				showButtons = showButtons.value,
				onBackPressed = { navigator.popBackStack() },
				onSubmit = {
					showButtons.value = false
					val strTicket = AppJson.encodeToString(order.Ticket)
					navigator.popBackStack()
					navigator.navigate(TicketsNavigation.RateAndSubmitTicketDialog(strTicket, it)) })
		}

		composable<TicketsNavigation.RateAndSubmitTicketDialog> { backStackEntry ->

			val argument = backStackEntry.toRoute<TicketsNavigation.RateAndSubmitTicketDialog>()
			val ticket = AppJson.decodeFromString<Ticket>(argument.strTicket)

			ticket.apply {
				val startDate = if(!this.StartDate.isNullOrEmpty())  "on ${this.StartDate}" else ""
				val strNumber = if(!this.Number.isNullOrEmpty()) "No. ${this.Number}" else ""
				val wayDocNumber = this.WayDocNumber ?: ""
				val ticketState = this.State

				val strConfirmOrDecline = if (argument.approveOrDecline) Res.string.sign.asState else Res.string.decline.asState
				val btnText = if (argument.approveOrDecline) Res.string.sign.asState else Res.string.decline.asState
				val btnColorResId = if (argument.approveOrDecline) cl_69BE28 else cl_e02020

				val strDate = DateTimeUtils.reformatDateTime(
					startDate, SERVER_DATE_TIME_PATTERN_SHORT, UI_DATE_PATTERN_SHORT
				                                            )?:""
				val dlgTitle = "$strConfirmOrDecline ${Res.string.performed_transportation.asState} $strNumber $strDate ?"
				val dlgText =
					if ('N' == ticketState) "(${Res.string.by_agreeing_on_the_transportation.asState} No. $wayDocNumber ${Res.string.and_the_customer_ticket.asState} $strNumber)" else ""

				//val showDialog = remember { mutableStateOf(true) }

				RateAndSubmitTicketDialog(
					dlgTitle,
				                          dlgText,
				                          btnText,
				                          btnColorResId,
				                          { navigator.popBackStack() },
				                          {						//if (it) 'R' else 'C',
					                          navigator.popBackStack()
				                          })
			}
		}
	}
}

fun redrawTicketsCountTextView(mTicketsTotal: Int): String {
	val strOrdersTotal = "$mTicketsTotal ${Res.string.tickets_total.asState}"
	return strOrdersTotal
}
