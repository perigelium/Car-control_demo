package eu.vctrl4.ui.online_board.order_list

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
import eu.vctrl4.storage.remote.entities.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.custom_views.composable.*
import org.jetbrains.compose.resources.*

@Composable
fun BoardOrderListScr(orders: List<BoardOrder>, modifier: Modifier, onItemSelected: (BoardOrder) -> Unit)
{
    Surface(modifier = modifier.fillMaxWidth(), shape = RectangleShape, color = Colors.transparent) {
        LazyColumn(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            items(orders.size) { position -> // , key = { orders[it].OrderId }

                val order = orders[position]

                BoardOrderListItem(order = order, { onItemSelected(order) })

                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun BoardOrderListItem(order: BoardOrder, onClick: () -> Unit)
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
	                text = "No.",
	                fontSize = 12.sp,
	                color = Colors.cl_8e8e93,
	                modifier = Modifier.align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.width(8.dp))

                Text(
	                text = order.OrderNumber ?: order.Number?:"",
	                fontSize = 12.sp,
	                color = Colors.cl_393854,
	                fontFamily = fontFamily,
	                modifier = Modifier.align(Alignment.Bottom)
                )
                Spacer(modifier = Modifier.width(16.dp))

                if (order.RentStartDate?.isNotBlank() == true)
                {
                    Image(
	                    painter = painterResource(Res.drawable.ic_passenger_car),
	                    contentDescription = "",
	                    Modifier
                            .size(16.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
	                    text = order.VehicleNumber ?: order.VehicleName?.substringBefore(" ")?:"",
	                    fontSize = 12.sp,
	                    color = Colors.cl_393854,
	                    fontFamily = fontFamily,
	                    modifier = Modifier.align(Alignment.Bottom)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                Modifier.fillMaxWidth()
            ) {
                order.titleTextAttrs.forEach { item ->

                    ImgTitleTextRowItem(titleText = item)

                    Spacer(modifier = Modifier.height(8.dp))
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview()
@Composable
fun BoardOrderListItemPreview()
{
    val order = BoardOrder()
    order.OrderNumber = "38476039460386"
    order.VehicleNumber = "03843046"
    order.RentStartDate = "2024-12-12T10:05:00"
    order.RentEndDate = "2024-12-12T11:05:00"

    val order1 = BoardOrder()
    order1.OrderId = "1111111111111"
    order1.OrderNumber = "38476039460386"
    order1.VehicleNumber = "03843046"
    order1.RentStartDate = "2024-12-12T10:05:00"
    order1.RentEndDate = "2024-12-12T11:05:00"

    BoardOrderListScr(orders = listOf(order, order1), modifier = Modifier, {})
}
