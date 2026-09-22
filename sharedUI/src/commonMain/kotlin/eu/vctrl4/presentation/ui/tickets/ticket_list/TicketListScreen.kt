package eu.vctrl4.presentation.ui.tickets.ticket_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.*
import eu.vctrl4.presentation.ui.customviews.composable.SearchField
import eu.vctrl4.presentation.ui.tickets.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.view_model.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun TicketListScreen(
	onFilterClick: () -> Unit,
	onSearchClick: (String) -> Unit,
	onScrolledToEnd: () -> Unit,
	onTicketConfirmClicked: (Order) -> Unit,
	events: (TicketListEvent) -> Unit,
	topAppBar: @Composable () -> Unit,
	state: TicketListViewState,
                    ) {
	val searchQuery = remember { mutableStateOf("") }
	val isSearchEnabled = remember { mutableStateOf(false) }
	var selectedOrder by remember { mutableStateOf(Order()) }

	Surface(
		modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_fafafa
	       ) {

		Box(modifier = Modifier.fillMaxSize()) {

			Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

				topAppBar()

				if (isSearchEnabled.value) {
					Spacer(modifier = Modifier.height(16.dp))
					SearchField(
						searchQuery = searchQuery.value,
						onQuerySubmitted = {
							isSearchEnabled.value = false
							onSearchClick(it)
						},
						searchHint = Res.string.search.asState,
						modifier = Modifier.padding(start = 16.dp, end = 16.dp),
						keyboardType = KeyboardType.NumberPassword
					           )
					val keyboardController = LocalSoftwareKeyboardController.current
					keyboardController?.show()
				}

				TicketList(
					orders = state.items,
				           modifier = Modifier.weight(1f, true),
				           onItemSelected = {},
				           onSubmitTicket = {
					           onTicketConfirmClicked(it)
				           },
				           onScrolledToEnd = { onScrolledToEnd() })

				BottomAppBar(
					actions = {
						IconButton(onClick = { onFilterClick() }) {
							Image(
								painter = painterResource(Res.drawable.ic_filter),
								contentDescription = "Filter",
								modifier = Modifier.size(24.dp)
							     )
						}

						Spacer(modifier = Modifier.weight(1f))

						IconButton(onClick = { isSearchEnabled.value = !isSearchEnabled.value }) {
							Image(
								painter = painterResource(Res.drawable.ic_search_ticket),
								contentDescription = "Filter",
								modifier = Modifier.size(24.dp)
							     )
						}
					},
					modifier = Modifier.padding(start = 8.dp, end = 8.dp).height(56.dp),
					containerColor = Colors.cl_f5f5f5
				            )

			}

		}
	}
}

@Preview
@Composable
fun TicketListScreenPreview() {
	val ordersTotal: MutableState<String> = remember { mutableStateOf("TOTAL 9999 TICKETS") }
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	TicketListScreen(
		{}, {}, {}, {}, {},
		topAppBar = {
			TopAppBarNavDrawer(
				title = Res.string.tickets.asState,
				itemsTotalLine = redrawTicketsCountTextView(
					mTicketsTotal = 100
				                                           ),
				onMenuBtnClick = { })
		},
		state = TicketListViewState(),
	                )
}