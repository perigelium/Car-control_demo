package eu.vctrl4.presentation.ui.tickets.ticket_list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.TICKET_STATES_MAP
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.ImgTitleTextRowItem
import eu.vctrl4.presentation.ui.customviews.composable.ThreeStringsRowItem
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*


@Composable
fun TicketList(
	orders: List<Order>, modifier: Modifier, onItemSelected: (Order) -> Unit, onSubmitTicket: (Order) -> Unit, onScrolledToEnd: () -> Unit
)
{
    val lazyColumnState = rememberLazyListState()
    val isPreview: Boolean = LocalInspectionMode.current

    if (!isPreview)
    {
        val isAtBottom = !lazyColumnState.canScrollForward

        LaunchedEffect(isAtBottom) {
            if (isAtBottom) onScrolledToEnd()
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

                TicketListItem(order = order, onClick = { onItemSelected(order) }, onSubmit = { onSubmitTicket(order) })

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun TicketListItem(
	order: Order,
	onClick: () -> Unit,
	onSubmit: () -> Unit,
)
{
    val fontFamily = FontFamily(
	    Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
    )
    val titleTextAttrLists = TitleTextAttrLists(order)

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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Column(
                    Modifier.fillMaxWidth()
                ) {

                    Text(
	                    text = TICKET_STATES_MAP[order.Ticket?.State]?:"",
	                    fontSize = 12.sp,
	                    color = Colors.cl_00549F,
	                    //fontFamily = fontFamily
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
	                        text = "No.",
	                        fontSize = 12.sp,
	                        color = Colors.cl_8e8e93,
	                        modifier = Modifier.align(Alignment.Bottom),
	                        //fontFamily = fontFamily
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
	                        text = order.Ticket?.Number ?: "",
	                        fontSize = 12.sp,
	                        color = Colors.cl_393854,
	                        //fontFamily = fontFamily,
	                        modifier = Modifier.align(Alignment.Bottom)
                        )
                        Spacer(modifier = Modifier.width(16.dp))

                        if (order.Ticket?.VehicleNumber?.isNotBlank() == true)
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
	                                text = order.Ticket?.VehicleNumber ?: "",
	                                fontSize = 12.sp,
	                                color = Colors.cl_393854,
	                                fontFamily = fontFamily,
	                                modifier = Modifier.align(Alignment.Bottom)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Colors.cl_d8)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                Modifier.fillMaxWidth()
            ) {
                titleTextAttrLists.ticketTitleTextAttrs.forEach { item ->

                    ImgTitleTextRowItem(titleText = item)

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(125.dp))

                Text(
	                text = Res.string.by_order.asState,
	                fontSize = 12.sp,
	                color = Colors.cl_8e8e93,
	                modifier = Modifier.align(Alignment.Bottom),
	                fontFamily = fontFamily,
                )

                Spacer(modifier = Modifier.width(40.dp))

                Text(
	                text = Res.string.by_ticket.asState,
	                fontSize = 12.sp,
	                color = Colors.cl_8e8e93,
	                modifier = Modifier.align(Alignment.Bottom),
	                fontFamily = fontFamily,
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(
                Modifier.fillMaxWidth()
            ) {
                titleTextAttrLists.ticketTitleTwoTextsAttrs.forEach { item ->

                    ThreeStringsRowItem(titleTexts = item)

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }

            val stateWaitingForConfirm = CastUtils.getKeyByValue(TICKET_STATES_MAP, Res.string.pending_confirmation.asState)

            if (stateWaitingForConfirm?.equals(order.Ticket?.State) == true)
            {
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = onSubmit,
                        border = BorderStroke(
                            1.dp, color = Colors.cl_CBCFD5
                        ),
                        content = {
                            Text(
	                            text = Res.string.confirm.asState, color = Colors.cl_00549F, fontSize = 16.sp
                            )
                        })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun TicketListItemPreview()
{
    val order = Order()
    order.Ticket = Ticket()
    order.OrderId = "2222222222222"
    order.Ticket?.Number = "38476039460386"
    order.Ticket?.VehicleNumber = "03843046"
    order.Ticket?.StartDate = "2024-12-12T10:05:00"
    order.Ticket?.EndDate = "2024-12-12T11:05:00"
    order.OrderType = 'C'
    order.Priority = 'L'
    order.ConsumerType = "Mob"
    order.CustomerCompanyName = Res.string.company.asState
    order.CustomerDepartmentName = Res.string.department.asState

    val order1 = Order()
    order1.OrderId = "1111111111111"
    order1.Number = "38476039460386"
    order1.VehicleName = "03843046"
    order1.CreateDate = "2024-12-12T10:05:00"
    order1.RentStartDate = "2024-12-12T11:05:00"
    order1.OrderType = 'P'
    order1.Priority = 'H'
    order1.ConsumerType = "Web"

    TicketList(orders = listOf(order, order1), Modifier, {}, {}, {})
}