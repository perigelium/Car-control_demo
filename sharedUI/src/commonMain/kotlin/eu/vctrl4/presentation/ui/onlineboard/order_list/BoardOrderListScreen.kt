package eu.vctrl4.presentation.ui.onlineboard.order_list

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
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.main.requests.OrderReportRequest
import eu.vctrl4.business.datasource.storage.entities.BoardOrder
import eu.vctrl4.business.datasource.storage.entities.OrderListRequest
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.BottomAppBarForLists
import eu.vctrl4.presentation.ui.customviews.composable.SearchField
import eu.vctrl4.presentation.ui.onlineboard.order_list.view_model.*
import eu.vctrl4.theme.*

@Composable
fun BoardOrderListScreen(
	state: BoardOrderListViewState,
	onFilterClick: () -> Unit,
	onSearchClick: (String) -> Unit,
	onOrderSelected: (BoardOrder) -> Unit,
	events: (BoardOrderListEvent) -> Unit,
	topAppBar: @Composable () -> Unit,
                        ) {
	val searchQuery = remember { mutableStateOf("") }
	val isSearchEnabled = remember { mutableStateOf(false) }

	Surface(
		modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_fafafa
	       ) {

		Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

			/*            val fontFamily = FontFamily(
							Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
						)*/

			topAppBar()

			if (isSearchEnabled.value) {
				Spacer(modifier = Modifier.height(16.dp))
				SearchField(
					searchQuery = searchQuery.value,
					onQuerySubmitted = {
						isSearchEnabled.value = false
						onSearchClick(it)
					},
					searchHint = Res.string.order_or_ticket_number.asState,
					modifier = Modifier.padding(start = 16.dp, end = 16.dp),
					keyboardType = KeyboardType.NumberPassword
				           )
				val keyboardController = LocalSoftwareKeyboardController.current
				keyboardController?.show()
			}

			BoardOrderListScr(
				orders = state.items,
				modifier = Modifier.weight(1f, true),
				onItemSelected = { onOrderSelected(it) })

			BottomAppBarForLists(isSearchEnabled, true, onFilterClick)
		}
	}
}

@Preview
@Composable
fun BoardOrderListScreenPreview() {
	val ordersTotal: MutableState<String> = remember { mutableStateOf("TOTAL 9999 ORDERS") }
	val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
	BoardOrderListScreen(
		state = BoardOrderListViewState(
			ordersRequest = OrderReportRequest(""),
			items = listOf(),
			firstStart = false,
			orderListRequest = OrderListRequest(),
			progressBarState = ProgressBarState.Idle
		                               ),
		onFilterClick = { },
		onSearchClick = { },
		onOrderSelected = { },
		events = {},
	                    ) {}
}