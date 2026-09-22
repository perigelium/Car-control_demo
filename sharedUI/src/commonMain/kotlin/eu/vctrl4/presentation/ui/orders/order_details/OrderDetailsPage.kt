package eu.vctrl4.presentation.ui.orders.order_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.TwoStringsColumnItem
import eu.vctrl4.presentation.ui.customviews.composable.TwoStringsColumnList
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun OrderDetailsPage(
	pageTitle: String, order: OrderDetails, titleTexts: List<IdNameValueName>
                    ) {
	val fontFamily = FontFamily(
		Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
	                           )
	val scrollState = rememberScrollState()

	Surface(
		modifier = Modifier.fillMaxSize(),
		shape = RoundedCornerShape(12.dp),
		border = BorderStroke(1.dp, color = Colors.cl_d8),
		color = Colors.white

	       ) {
		Column(
			Modifier.fillMaxSize().verticalScroll(scrollState).padding(horizontal = 16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		      ) {
			Box(
				Modifier.fillMaxWidth(0.9f).height(4.dp).background(Colors.cl_69BE28)
			   )

			Spacer(modifier = Modifier.height(24.dp))

			order.PlannedAmount?.let {
				val strAmount = "${(it / 10).toInt()} \u20ac"

				Text(
					text = strAmount,
					fontFamily = fontFamily,
					fontSize = 26.sp,
					maxLines = 1,
					color = Colors.cl_2e3b4c
				    )


				Spacer(modifier = Modifier.height(8.dp))

				Text(
					text = Res.string.planned_order_cost.asState,
					fontSize = 12.sp,
					color = Colors.cl_2e3b4c,
					fontFamily = fontFamily,
					maxLines = 1,
					fontWeight = FontWeight.Thin
				    )
				Spacer(modifier = Modifier.height(24.dp))
			}

			Text(
				text = pageTitle,
				fontFamily = fontFamily,
				fontSize = 14.sp,
				maxLines = 1,
				fontWeight = FontWeight.Bold,
				color = Colors.cl_393854
			    )

			Spacer(modifier = Modifier.height(24.dp))

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth()
			      ) {
				titleTexts.forEach { nameValue ->

					if (nameValue.isChecked == null) {
						TwoStringsColumnItem(titleSubtitle = nameValue)
					} else {
						CheckBoxAndTextRow(item = nameValue)
					}
					Spacer(modifier = Modifier.height(16.dp))
				}
				Spacer(modifier = Modifier.height(48.dp))
			}
		}
	}
}

@Composable
private fun CheckBoxAndTextRow(item: IdNameValueName) {
	Row(
		Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.Start,
		verticalAlignment = Alignment.CenterVertically
	   ) {

		Image(
			painter = painterResource(resource = if (item.isChecked == true) Res.drawable.ic_chk_white_on_blue else Res.drawable.ic_chk_gray),
			contentDescription = "is checked",
			modifier = Modifier.size(24.dp)
		     )

		Spacer(modifier = Modifier.width(16.dp))

		Text(text = item.toString(), fontSize = 14.sp, color = Colors.cl_232323)
	}
}

@Composable
fun OrderDetailsTransportationPage(
	pageTitle: String,
	order: OrderDetails,
	titleTexts1: List<IdNameValueName>,
	titleTexts2: List<IdNameValueName>,
	titleTexts3: List<IdNameValueName>
                                  ) {
	val fontFamily = FontFamily(
		Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
	                           )
	val scrollState = rememberScrollState()

	Surface(
		modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
		shape = RoundedCornerShape(12.dp),
		border = BorderStroke(1.dp, Colors.cl_d8),
		color = Colors.white
	       ) {
		Column(
			Modifier.fillMaxWidth().padding(horizontal = 16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		      ) {
			Box(
				Modifier.fillMaxWidth(0.9f).height(4.dp).background(Colors.cl_69BE28)
			   )

			Spacer(modifier = Modifier.height(24.dp))

			order.PlannedAmount?.let {
				val strAmount = "${(it / 10).toInt()} \u20ac"

				Text(
					text = strAmount,
					fontFamily = fontFamily,
					fontSize = 26.sp,
					maxLines = 1,
					color = Colors.cl_2e3b4c
				    )


				Spacer(modifier = Modifier.height(8.dp))

				Text(
					text = Res.string.planned_order_cost.asState,
					fontSize = 12.sp,
					color = Colors.cl_2e3b4c,
					fontFamily = fontFamily,
					maxLines = 1,
					fontWeight = FontWeight.Thin
				    )
				Spacer(modifier = Modifier.height(24.dp))
			}

			Text(
				text = pageTitle,
				fontFamily = fontFamily,
				fontSize = 14.sp,
				maxLines = 1,
				fontWeight = FontWeight.Bold,
				color = Colors.cl_393854
			    )

			Spacer(modifier = Modifier.height(24.dp))

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth()
			      ) {
				titleTexts1.forEach { nameValue ->

					if (nameValue.isChecked == null) {
						TwoStringsColumnItem(titleSubtitle = nameValue)
					} else {
						CheckBoxAndTextRow(item = nameValue)
					}
					Spacer(modifier = Modifier.height(16.dp))
				}
			}

			if (titleTexts2.isNotEmpty()) {
				TwoStringsColumnList(titleTexts = titleTexts2)
			}

			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier = Modifier.fillMaxWidth()
			      ) {
				titleTexts3.forEach { nameValue ->

					if (nameValue.isChecked == null) {
						TwoStringsColumnItem(titleSubtitle = nameValue)
					} else {
						CheckBoxAndTextRow(item = nameValue)
					}
					Spacer(modifier = Modifier.height(16.dp))
				}
			}
			Spacer(modifier = Modifier.height(64.dp))
		}
	}
}

@Preview()
@Composable
fun OrderDetailsPreview() {
	val order = OrderDetails()
	order.OrderId = "2222222222222"
	order.Number = "38476039460386"
	order.VehicleName = "03843046"
	order.CreateDate = "2024-12-12T10:05:00"
	order.RentStartDate = "2024-12-12T11:05:00"
	order.OrderType = 'C'
	order.Priority = 'L'
	order.ConsumerType = "Mob"
	order.PlannedAmount = 9999f

	val idName = IdNameValueName()
	idName.Name = Res.string.name.asState
	idName.valueName = Res.string.value.asState
	idName.isChecked = false

	val idName1 = IdNameValueName()
	idName1.Name = Res.string.name.asState
	idName1.valueName = Res.string.value.asState

	OrderDetailsPage(
		pageTitle = Res.string.additional_information_caps.asState,
		order = order,
		titleTexts = listOf(idName, idName1)
	                )
}