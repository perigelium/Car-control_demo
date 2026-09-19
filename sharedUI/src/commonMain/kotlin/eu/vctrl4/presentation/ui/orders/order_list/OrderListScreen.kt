package eu.vctrl4.presentation.ui.orders.order_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.orders.order_list.view_model.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.custom_views.composable.*
import org.jetbrains.compose.resources.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(
	onScrolledToEnd: () -> Unit,
	onFilterClick: () -> Unit,
	onSearchClick: (String) -> Unit,
	onOrderSelected: (String?) -> Unit,
	onActionInvoked: (Order) -> Unit,
	onFabClick: () -> Unit,
	topAppBar: @Composable () -> Unit,
	items:List<Order>
)
{
    val searchQuery = remember { mutableStateOf("") }
    val isSearchEnabled = remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_f5f5f5
    ) {

        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

            val fontFamily = FontFamily(
	            Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
            )

            topAppBar()

            if (isSearchEnabled.value)
            {
                Spacer(modifier = Modifier.height(16.dp))
                SearchField(
                    searchQuery = searchQuery.value,
                    onQuerySubmitted = {
                        isSearchEnabled.value = false
                        onSearchClick(it)
                    },
                    searchHint = Res.string.order_number.asState,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                    keyboardType = KeyboardType.NumberPassword
                )
                val keyboardController = LocalSoftwareKeyboardController.current
                keyboardController?.show()
            }

            Scaffold(modifier = Modifier, floatingActionButton = { // .nestedScroll(scrollBehavior.nestedScrollConnection)
                Box() {
                    FloatingActionButton(
	                    onClick = { onFabClick() },
	                    shape = CircleShape,
	                    modifier = Modifier
                            .align(Alignment.Center)
                            .size(56.dp)
                            .offset(y = 40.dp),
	                    containerColor = Colors.cl_00549F,
	                    contentColor = Color.White,
	                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }, floatingActionButtonPosition = FabPosition.Center,
                content = { innerPadding->

	                OrderList(
		                orders = items,
		                modifier = Modifier.weight(1f, true).padding(top = innerPadding.calculateTopPadding()),
		                onItemSelected = { onOrderSelected(it.OrderId) },
		                onScrolledToEnd = { onScrolledToEnd() },
		                onActionInvoked = { onActionInvoked(it) })
            }, bottomBar = {
                    BottomAppBarWithFilterAndSearch(onFilterClick = onFilterClick, isSearchEnabled) // scrollBehavior
            },
                contentWindowInsets = WindowInsets(0,0,0,0)
					)
        }
    }
}

@Preview
@Composable
fun OrderListScreenPreview()
{
    val ordersState: MutableState<OrderListViewState> = remember { mutableStateOf(OrderListViewState())}
    ordersState.value.items = listOf(Order(), Order(), Order(), Order())
    val ordersTotal: MutableState<String> = remember { mutableStateOf("TOTAL 9999 ORDERS") }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    OrderListScreen(
        items = ordersState.value.items,
        onScrolledToEnd = {},
        onFilterClick = {},
        onSearchClick = {},
        onOrderSelected = {},
        onActionInvoked = {},
        onFabClick = {},
        topAppBar = {}
    )
}