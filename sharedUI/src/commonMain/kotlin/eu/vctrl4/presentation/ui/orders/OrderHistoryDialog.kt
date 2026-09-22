package eu.vctrl4.presentation.ui.orders

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.constants.Constants.ORDER_STATES_MAP
import eu.vctrl4.business.datasource.storage.entities.OrderHistoryItem
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.TopAppBarCustom
import eu.vctrl4.presentation.ui.customviews.composable.TwoSideStringsRowItem
import eu.vctrl4.presentation.utils.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*


@Composable
fun OrderHistoryListItem(
    strTimeSrv: String?, statePairs: List<Pair<String, String>>, lStrings: List<Pair<String, String?>>, consumerTypeId: DrawableResource?
)
{
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, Colors.cl_d0d0d0)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val strTime = DateTimeUtils.reformatDateTime(
	                strTimeSrv,
	                DateTimeUtils.SERVER_DATE_TIME_PATTERN_SHORT,
	                DateTimeUtils.UI_DATE_TIME_PATTERN_LONG
                                                            )
	            TwoSideStringsRowItem(titleSubtitle = Pair("Date/Time:", strTime ?: ""), false)

                consumerTypeId?.apply {
                    Image(painter = painterResource(this), contentDescription = "", Modifier.size(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Colors.cl_CBCFD5, shape = RectangleShape)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TwoSideStringsRowItem(
	                titleSubtitle = Pair(
		                statePairs[0].first,
		                statePairs[0].second
	                                    ), false
                                     )
	            TwoSideStringsRowItem(
		            titleSubtitle = Pair(
			            statePairs[1].first,
			            statePairs[1].second
		                                ), false
	                                 )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxWidth()
            ) {
                lStrings.forEach() { item ->

                    if (item.second?.isNotBlank() == true)
                    {
	                    TwoSideStringsRowItem(titleSubtitle = item)

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OrderHistoryDialog(dialogTitle: String, listItems: List<OrderHistoryItem>, onDismiss: () -> Unit)
{
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = true, dismissOnBackPress = true),
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth(0.99f),
            shape = RoundedCornerShape(18.dp),
            color = Colors.cl_f5f5f5
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp)
                , horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBarCustom(
	                backBtnTxt = "",
	                onBackBtnClick = { onDismiss() },
	                titleTxt = dialogTitle,
	                backColorId = Colors.cl_f5f5f5
                               )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, false),
                ) {
                    items(listItems.size) { position ->

                        val histItem = listItems[position]

                        val lStateItems = listOf(
	                        Pair(Res.string.state.asState, ORDER_STATES_MAP.get(histItem.State)  ?: ""),
	                        Pair(Res.string.is_protested.asState, if (histItem.IsProtest) Res.string.Yes.asState else Res.string.no.asState)
                        )

                        val lItems = listOf(
	                        Pair(Res.string.cancel_reason.asState, histItem.CancelReasonName),
	                        Pair(Res.string.user.asState, histItem.UserName),
	                        Pair(Res.string.comment.asState, histItem.Comment),
	                        Pair(Res.string.measures_taken.asState, histItem.MeasuresTaken)
                        )

                        OrderHistoryListItem(
	                        histItem.Date,
	                        lStateItems,
	                        lStrings = lItems,
	                        Constants.CONSUMER_TYPES_ICONS.get(histItem.ConsumerType)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun TwoSideStringsListDialogPreview()
{
    val orderHistoryItem = OrderHistoryItem()
    orderHistoryItem.StateName = "Status"
    orderHistoryItem.UserName = "UserName"
    orderHistoryItem.Comment = "Comment"
    orderHistoryItem.CancelReasonName = "CancelReason"
    orderHistoryItem.MeasuresTaken = "MeasuresTaken"
    orderHistoryItem.ConsumerType = "Mob"
    orderHistoryItem.Date = "2024-12-24T12:24:00"

    val orderHistoryItem2 = OrderHistoryItem()
    orderHistoryItem2.StateName = "Status"
    orderHistoryItem2.UserName = "UserName"
    orderHistoryItem2.Comment = "Comment"
    orderHistoryItem2.CancelReasonName = ""
    orderHistoryItem2.MeasuresTaken = "MeasuresTaken"
    orderHistoryItem2.ConsumerType = "Web"
    orderHistoryItem2.IsProtest = true

    OrderHistoryDialog(Res.string.order_change_history.asState, listOf(orderHistoryItem, orderHistoryItem2), {})
}