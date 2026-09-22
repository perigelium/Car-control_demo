package eu.vctrl4.presentation.ui.orders.order_details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.Constants.BOTTOM_APP_BAR_NAV_ITEMS
import eu.vctrl4.business.constants.Constants.ORDER_STATES_MAP
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.BottomAppBarOrderDetails
import eu.vctrl4.presentation.ui.customviews.composable.CenteredAppBar
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

val stepsCount: Int = BOTTOM_APP_BAR_NAV_ITEMS.size - 1

@Composable
fun OrderDetailsScreen(
	order: OrderDetails,
	titleTexts: List<IdNameValueName>,
	titleTextsTranspTop: List<IdNameValueName>,
	titleTextsTranspOptionTypes: List<IdNameValueName>,
	titleTextsTranspBottom: List<IdNameValueName>,
	onBackPressed: () -> Unit,
	onPerformAction: (String) -> Unit,
	navigate: (String) -> Unit
                      ) {
	var curStepNumber by remember { mutableIntStateOf(0) }
	val remSelItemIndex: MutableIntState = remember { mutableIntStateOf(curStepNumber) }
	var forwardBackward by remember { mutableStateOf(true) }

	val remStrSubtitle: MutableState<String> =
		remember { mutableStateOf(BOTTOM_APP_BAR_NAV_ITEMS.get(remSelItemIndex.intValue).title) }
	val stFabImageVector: MutableState<ImageVector> =
		remember { mutableStateOf(Icons.AutoMirrored.Default.ArrowForward) }

	Surface(modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.white) {
		Column(
			modifier = Modifier.fillMaxSize().background(Colors.cl_white),
			horizontalAlignment = Alignment.CenterHorizontally
		      ) {
			val titleText =
				if (order.Number != null) "No. " + order.Number else Res.string.order.asState

			CenteredAppBar(
				onBackBtnClick = { onBackPressed() },
				backColorId = Colors.cl_00549F,
				titleTxt = titleText,
				titleColorId = Colors.white,
				orderState = order.State ?: "",
				onActionInvoked = { onPerformAction(it) })

			order.State?.let {
				Row(
					modifier = Modifier.fillMaxWidth().background(Colors.cl_00549F),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.Center
				   ) {
					Icon(
						painter = painterResource(Res.drawable.ic_done),
						contentDescription = Res.string.order_status.asState,
						modifier = Modifier.size(24.dp),
						tint = Colors.white
					    )
					Spacer(modifier = Modifier.width(16.dp))

					Text(
						text = ORDER_STATES_MAP.get(order.State) ?: "",
						color = Colors.white,
						fontSize = 14.sp,
						maxLines = 1
					    )
				}
				Box(
					modifier = Modifier.height(20.dp).fillMaxWidth().background(Colors.cl_00549F)
				   )
			}

			Scaffold(
				modifier = Modifier.background(color = Colors.white).fillMaxSize(),
			         floatingActionButton = { // .nestedScroll(scrollBehavior.nestedScrollConnection)

				         Box() {
					         FloatingActionButton(
						         onClick = {
							         if (forwardBackward && curStepNumber < stepsCount - 1) curStepNumber++
							         else if (!forwardBackward && curStepNumber > 0) curStepNumber--
							         if (curStepNumber == stepsCount - 1 || curStepNumber == 0) forwardBackward =
								         !forwardBackward

							         navigate(BOTTOM_APP_BAR_NAV_ITEMS.get(curStepNumber).name)

							         remStrSubtitle.value =
								         BOTTOM_APP_BAR_NAV_ITEMS.get(curStepNumber).title
							         remSelItemIndex.intValue = curStepNumber
							         stFabImageVector.value =
								         if (forwardBackward) Icons.AutoMirrored.Default.ArrowForward else Icons.AutoMirrored.Default.ArrowBack
						         },
						         shape = CircleShape,
						         modifier = Modifier.align(Alignment.Center).size(56.dp)
							         .offset(y = 40.dp),
						         containerColor = Colors.cl_00549F,
						         contentColor = Color.White,
						         elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
					                             ) {
						         Icon(
							         imageVector = stFabImageVector.value,
							         contentDescription = null,
							         modifier = Modifier.size(24.dp)
						             )
					         }
				         }
			         },
			         floatingActionButtonPosition = FabPosition.End,
			         content = { innerPadding ->

				         Column(
					         modifier = Modifier.padding(
						         top = innerPadding.calculateTopPadding(),
						         bottom = innerPadding.calculateTopPadding() + 60.dp
					                                    ).fillMaxSize()
				               ) {
					         if ("nav_transportation" == BOTTOM_APP_BAR_NAV_ITEMS.get(curStepNumber).name) {
						         OrderDetailsTransportationPage(
							         pageTitle = remStrSubtitle.value,
							         order = order,
							         titleTexts1 = titleTextsTranspTop,
							         titleTexts2 = titleTextsTranspOptionTypes,
							         titleTexts3 = titleTextsTranspBottom
						                                       )
					         } else {
						         OrderDetailsPage(remStrSubtitle.value, order, titleTexts)
					         }
				         }
			         },
			         bottomBar = {
				         BottomAppBarOrderDetails(
					         items = BOTTOM_APP_BAR_NAV_ITEMS,
					         remSelItemIndex = remSelItemIndex,
					         onNavigationItemClick = { route ->
						         navigate(route)
						         curStepNumber = BOTTOM_APP_BAR_NAV_ITEMS.indexOf(
							         (BOTTOM_APP_BAR_NAV_ITEMS.firstOrNull { it.name == route })
								         ?: 0
						                                                         )
						         remStrSubtitle.value =
							         BOTTOM_APP_BAR_NAV_ITEMS.get(curStepNumber).title
					         })
			         },
			         contentWindowInsets = WindowInsets(0, 0, 0, 0)
			        )
		}
	}
}