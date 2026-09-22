package eu.vctrl4.presentation.ui.tickets.ticket_confirmation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.TICKET_STATES_MAP
import eu.vctrl4.business.datasource.network.main.requests.DoneRequest
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.ImgTitleTextRowItem
import eu.vctrl4.presentation.ui.customviews.composable.ThreeStringsRowItem
import eu.vctrl4.presentation.ui.customviews.composable.TopAppBarCustom
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*


@Composable()
fun TicketConfirmationScreen(
	order: Order, showButtons: Boolean, onSubmit: (Boolean) -> Unit, onBackPressed: () -> Unit
                            ) {
	val fontFamily = FontFamily(
		Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
	                           )
	val titleTextAttrLists = TitleTextAttrLists(order)

	Surface(
		modifier = Modifier.fillMaxWidth(),
		shape = RectangleShape,
		border = BorderStroke(1.dp, Colors.cl_d8),
		color = Colors.white
	       ) {
		Column(
			Modifier.fillMaxWidth()
		      ) {

			TopAppBarCustom(
				backBtnTxt = "",
				onBackBtnClick = { onBackPressed() },
				titleTxt = Res.string.ticket_confirmation.asState,
				backColorId = Colors.cl_f5f5f5,
				height = 56
			               )

			Spacer(modifier = Modifier.height(16.dp))

			Column(
				Modifier.fillMaxWidth().padding(horizontal = 16.dp)
			      ) {
				titleTextAttrLists.ticketConfirmTitleTextAttrs.forEach { item ->

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
				Modifier.fillMaxWidth().padding(horizontal = 16.dp)
			      ) {
				titleTextAttrLists.ticketTitleTwoTextsAttrs.forEach { item ->

					ThreeStringsRowItem(titleTexts = item)

					Spacer(modifier = Modifier.height(12.dp))
				}
			}

			val stateWaitingForConfirm =
				CastUtils.getKeyByValue(TICKET_STATES_MAP, Res.string.pending_confirmation.asState)

			if (stateWaitingForConfirm?.equals(order.Ticket?.State) == true && showButtons) {

				Spacer(modifier = Modifier.height(8.dp))

				Box(
					Modifier.fillMaxWidth().height(1.dp).background(Colors.cl_d8)
				   )

				Spacer(modifier = Modifier.height(24.dp))

				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceEvenly
				   ) {

					Box(
						modifier = Modifier.height(48.dp).defaultMinSize(minWidth = 145.dp)
						.clip(shape = RoundedCornerShape(12.dp)).clickable { onSubmit(true) }
						.background(Colors.cl_69BE28), contentAlignment = Alignment.Center) {
						Text(
							text = Res.string.approve.asState,
							textAlign = TextAlign.Center,
							color = Colors.white,
							fontSize = 16.sp,
							modifier = Modifier.padding(horizontal = 24.dp)
						    )
					}

					Box(
						modifier = Modifier.height(48.dp).defaultMinSize(minWidth = 145.dp)
						.clip(shape = RoundedCornerShape(12.dp)).clickable { onSubmit(false) }
						.background(Colors.cl_e02020), contentAlignment = Alignment.Center) {
						Text(
							text = Res.string.decline.asState,
							textAlign = TextAlign.Center,
							color = Colors.white,
							fontSize = 16.sp,
							modifier = Modifier.padding(horizontal = 24.dp)
						    )
					}
				}
			}

			Spacer(modifier = Modifier.height(16.dp))
		}
	}
}

@Preview
@Composable
fun TicketConfirmationPreview() {
	val order = Order()
	order.PriorityId = "L"
	order.Ticket = Ticket()
	order.OrderId = "2222222222222"
	order.Ticket?.Number = "38476039460386"
	order.Ticket?.VehicleNumber = "03843046"
	order.Ticket?.StartDate = "2024-12-12T10:05:00"
	order.Ticket?.EndDate = "2024-12-12T11:05:00"
	order.Ticket?.State = 'N'
	order.Ticket?.Mileage = 55
	order.Ticket?.EquipmentTime = 180
	order.Ticket?.ConfirmedTime = 240
	order.OrderType = 'C'
	order.Priority = 'L'
	order.ConsumerType = "Mob"
	order.CustomerCompanyName = Res.string.company.asState
	order.CustomerDepartmentName = Res.string.department.asState
	order.DoneRequest = DoneRequest()
	order.DoneRequest?.RequestCreatorName = Res.string.very_responsible.asState
	order.DoneRequest?.RequestDate = "2024-12-12T10:05:00"
	order.DoneRequest?.RequestComment = Res.string.comment_short.asState

	TicketConfirmationScreen(order = order, showButtons = true, {}, {})
}