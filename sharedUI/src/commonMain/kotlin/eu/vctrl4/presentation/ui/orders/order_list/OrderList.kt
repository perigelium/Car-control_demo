package eu.vctrl4.presentation.ui.orders.order_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.ORDER_STATES_MAP
import eu.vctrl4.business.constants.Constants.ORDER_TYPES_ICONS
import eu.vctrl4.business.constants.Constants.PRIORITY_COLORS_INT
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.entities.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.custom_views.composable.*
import org.jetbrains.compose.resources.*


@Composable
fun OrderList(
	orders: List<Order>,
	modifier: Modifier,
	onItemSelected: (Order) -> Unit,
	onScrolledToEnd: () -> Unit,
	onActionInvoked: (Order) -> Unit
)
{
    val lazyColumnState = rememberLazyListState()
    val layoutInfo by remember { derivedStateOf { lazyColumnState.layoutInfo } }
    val endReached = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

    if (endReached)
    {
        LaunchedEffect(Unit) {
            onScrolledToEnd()
        }
    }

    Surface(modifier = modifier.fillMaxWidth(), shape = RectangleShape, color = Colors.transparent) {
        LazyColumn(
            state = lazyColumnState,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(orders.size) { position -> // , key = { orders[it].OrderId }

                val order = orders[position]

                OrderListItem(
	                order = order,
	                orderTypeRes = ORDER_TYPES_ICONS.get(order.OrderType),
	                priorityColorRes = PRIORITY_COLORS_INT.get(order.Priority),
	                consumerTypeRes = Constants.CONSUMER_TYPES_ICONS.get(order.ConsumerType),
	                orderStatus = ORDER_STATES_MAP.get(order.State) ?:"",
	                onClick = { onItemSelected(order) },
	                onActionInvoked = { onActionInvoked(order) })

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun OrderListItem(
	order: Order,
	orderTypeRes: DrawableResource?,
	priorityColorRes: Color?,
	consumerTypeRes: DrawableResource?,
	orderStatus: String,
	onClick: () -> Unit,
	onActionInvoked: () -> Unit
)
{
    val fontFamily = FontFamily(
	    Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
    )

    Surface(
	    modifier = Modifier.fillMaxWidth(),
	    shape = RoundedCornerShape(12.dp),
	    border = BorderStroke(1.dp, Colors.cl_d8),
	    color = Colors.white
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable { onClick() }) {

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(22.dp))

                if (priorityColorRes != null)
                {
                    Box(
                        Modifier
                            .width(147.dp)
                            .height(4.dp)
                            .background(priorityColorRes))

                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (consumerTypeRes != null)
            {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        painter = painterResource(consumerTypeRes), contentDescription = "", Modifier.size(24.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

                if (orderTypeRes != null)
                {
                    Image(
                        painter = painterResource(orderTypeRes), contentDescription = "", Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    Modifier.fillMaxWidth()
                ) {

                    Text(
	                    text = orderStatus,
	                    fontSize = 12.sp,
	                    color = Colors.cl_00549F,
	                    fontFamily = fontFamily
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
	                        text = "No.",
	                        fontSize = 12.sp,
	                        color = Colors.cl_8e8e93,
	                        modifier = Modifier.align(Alignment.CenterVertically),
	                        fontFamily = fontFamily
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
	                        text = order.Number ?: "",
	                        fontSize = 12.sp,
	                        color = Colors.cl_393854,
	                        fontFamily = fontFamily,
	                        modifier = Modifier.align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        if (order.VehicleName?.isNotBlank() == true)
                        {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
	                                painter = painterResource(Res.drawable.ic_passenger_car),
	                                contentDescription = "",
	                                Modifier
                                        .size(16.dp)
                                        .align(Alignment.CenterVertically)
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                Text(
	                                text = order.VehicleName ?: "",
	                                fontSize = 12.sp,
	                                color = Colors.cl_393854,
	                                fontFamily = fontFamily,
	                                modifier = Modifier.align(Alignment.CenterVertically)
                                )
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                Modifier.fillMaxWidth()
            ) {
                val titleTextAttrLists = TitleTextAttrLists(order)
                titleTextAttrLists.orderTitleTextAttrs.forEach { item ->

                    ImgTitleTextRowItem(titleText = item)

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
	                painter = painterResource(Res.drawable.ic_vert_three_points),
	                contentDescription = "",
	                Modifier
                        .width(40.dp)
                        .height(13.dp)
                        .clickable { onActionInvoked() })
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun OrderListItemPreview()
{
	//PreviewContextConfigurationEffect()
    val order = Order()
    order.OrderId = "2222222222222"
    order.Number = "38476039460386"
    order.VehicleName = "03843046"
    order.CreateDate = "2024-12-12T10:05:00"
    order.RentStartDate = "2024-12-12T11:05:00"
    order.OrderType = 'C'
    order.Priority = 'L'
    order.ConsumerType = "Mob"

    val order1 = Order()
    order1.OrderId = "1111111111111"
    order1.Number = "38476039460386"
    order1.VehicleName = "03843046 - elkdsflskeslkje"
    order1.CreateDate = "2024-12-12T10:05:00"
    order1.RentStartDate = "2024-12-12T11:05:00"
    order1.OrderType = 'P'
    order1.Priority = 'H'
    order1.ConsumerType = "Web"

    OrderList(orders = listOf(order, order1), Modifier, {}, {},{})
}