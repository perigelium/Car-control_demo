package eu.vctrl4.presentation.ui.onlineboard.map

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.*
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun OsmMapScreen(
	viewState: VehicleTrackViewState, modifier: Modifier = Modifier, onBackClicked : () -> Unit
                ) {
	var isFooterVisible by remember { mutableStateOf(true) }

	@Composable
	fun FooterRow(label: String, value: String) {
		Row(modifier = Modifier.fillMaxWidth()) {
			Text(text = label, modifier = Modifier.weight(1f), fontSize = 14.sp, color = Color.Gray)

			Text(
				text = value,
				modifier = Modifier.weight(1f),
				fontSize = 14.sp,
				fontWeight = FontWeight.Medium,
				color = Color.DarkGray
			    )
		}
	}

	Box(
		modifier = modifier.fillMaxSize().pointerInput(Unit) {
			awaitPointerEventScope {
				while (true) {
					val event = awaitPointerEvent()
					event.changes.forEach { pointerChange ->
						pointerChange.consume()
					}
				}
			}
		}) {

		PlatformOsmMap(
			viewState = viewState, modifier = Modifier.fillMaxSize().padding(top = 32.dp))

		viewState.order?.let { order ->

			Card(
				modifier = Modifier.fillMaxWidth(),
				shape = RectangleShape,
				colors = CardDefaults.cardColors(containerColor = Color.White),
				elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
			    ) {
				Row(
					modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
					verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
				   ) {

					Box(contentAlignment = Alignment.Center,
						modifier = Modifier.size(48.dp).clickable(onClick = { onBackClicked() })) {
						Icon(
							painter = painterResource(Res.drawable.ic_arrow_left_white),
							contentDescription = "Back home",
							tint = Color.Gray,
							modifier = Modifier.size(20.dp)
						    )
					}
					Spacer(modifier = Modifier.width(16.dp))

					Column {
						Text(text = Res.string.order_number.asState, color = Colors.cl_393854, fontSize = 14.sp)
						Text(text = Res.string.vehicle_number.asState, color = Colors.cl_393854, fontSize = 14.sp)
					}
					Spacer(modifier = Modifier.width(16.dp))
					Column(modifier = Modifier.weight(1f)) {
						Text(
							text = order.OrderNumber ?: order.Number ?: "",
							color = Colors.cl_393854,
							fontSize = 14.sp
						    )
						Text(
							text = order.VehicleNumber ?: order.VehicleName?.substringBefore(" ")
							?: "", color = Colors.cl_393854, fontSize = 14.sp
						    )
					}
				}
			}

			Column(
				modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
			      ) {
				Box(
					modifier = Modifier.fillMaxWidth().background(Color.White).clickable {
					isFooterVisible = !isFooterVisible
				}.padding(vertical = 8.dp, horizontal = 16.dp),
				    contentAlignment = Alignment.CenterEnd
				   ) {
					Icon(
						painter = painterResource(
							if (isFooterVisible) Res.drawable.ic_tick_down_black else Res.drawable.ic_tick_up_black
						                         ),
						contentDescription = "Toggle Footer Panel",
						tint = Color.Unspecified,
						modifier = Modifier.size(20.dp)
					    )
				}

				AnimatedVisibility(
					visible = isFooterVisible,
					enter = expandVertically(animationSpec = tween(500)),
					exit = shrinkVertically(animationSpec = tween(500))
				                  ) {
					viewState.order.let { order ->
						Card(
							modifier = Modifier.fillMaxWidth(),
							shape = RoundedCornerShape(0.dp),
							colors = CardDefaults.cardColors(containerColor = Color.White),
							elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
						    ) {
							Column(
								modifier = Modifier.fillMaxWidth().heightIn(max = 240.dp)
									.verticalScroll(rememberScrollState()).padding(16.dp),
								verticalArrangement = Arrangement.spacedBy(6.dp)
							      ) {
								FooterRow(
									label = Res.string.rent_start.asState, value = DateTimeUtils.reformatDateTime(
										order.RentStartDate, DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
										DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
									                                                             )
										?: "No data"
								         )
								FooterRow(
									label = Res.string.rent_finish.asState, value = DateTimeUtils.reformatDateTime(
										order.RentEndDate,
										DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
										DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
									                                                              )
										?: "No data"
								         )
								FooterRow(
									label = Res.string.driver_name.asState,
									value = viewState.order.Ticket?.DriverName ?: "No data"
								         )
								FooterRow(
									label = Res.string.driver_phone.asState,
									value = viewState.order.Ticket?.DriverPhone ?: "No data"
								         )
								FooterRow(
									label = Res.string.final_mileage.asState,
									value = viewState.order.Ticket?.Mileage?.toString() ?: "No data"
								         )
								val confirmedTimeStr =
									viewState.order.Ticket?.ConfirmedTime?.toString()
								val strRentTimeUI =
									if (confirmedTimeStr != null && confirmedTimeStr.isNotEmpty() && confirmedTimeStr.all { it.isDigit() }) {
										DateTimeUtils.getTimerAsStringFromMins(order.Ticket?.ConfirmedTime!!)
									} else {
										"No data"
									}

								FooterRow(
									label = Res.string.rental_period.asState, value = strRentTimeUI
								         )
							}
						}
					}
				}
			}
		}
	}
}