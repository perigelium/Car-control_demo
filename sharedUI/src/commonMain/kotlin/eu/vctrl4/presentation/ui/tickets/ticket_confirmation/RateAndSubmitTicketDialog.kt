package eu.vctrl4.ui.tickets.ticket_confirmation


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
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.component.*
import eu.vctrl4.presentation.ui.customviews.composable.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.custom_views.composable.*
import org.jetbrains.compose.resources.*


@Composable
fun RateAndSubmitTicketDialog(title: String?, dlgText:String?, submitBtnLabel:String, btnColorResId: Color, onDismiss: () -> Unit, onSubmit: (Pair<Int, String>) -> Unit)
{
    val strComment = remember { mutableStateOf("") }
    val ratingState = remember { mutableIntStateOf(0) }
    val ratingChosen = remember { mutableIntStateOf(0) }
	val isPopupVisible = remember { mutableStateOf(false) }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = true, dismissOnBackPress = true),
        onDismissRequest = onDismiss
    ) {
        Surface(modifier = Modifier.fillMaxSize(), shape = RoundedCornerShape(18.dp), Colors.cl_f5f5f5) {

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(all = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TopAppBarCustom(
	                backBtnTxt = "",
	                onBackBtnClick = { onDismiss() },
	                titleTxt = title ?: "",
	                backColorId = Colors.cl_f5f5f5,
	                height = 80
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(1.dp)
                        .background(color = Colors.cl_d8)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = 12.dp), horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    val fontFamily = FontFamily(
	                    Font(Res.font.pfbeausanspro_regular, FontWeight.SemiBold),
                    )

                    Text(
	                    text = dlgText?:"",
	                    fontSize = 15.sp,
	                    color = Colors.cl_232323,
	                    fontFamily = fontFamily,
	                    fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {

                        Text(
	                        text = Res.string.rate_the_work_performed.asState,
	                        fontSize = 13.sp,
	                        color = Colors.cl_232323,
	                        fontFamily = fontFamily,
	                        fontWeight = FontWeight.Normal
                        )

                        FiveStarsRow(ratingState.intValue, 150, 24, onItemSelected = {
                            ratingState.intValue = it
                            ratingChosen.value = it })
                    }

                    Spacer(modifier = Modifier.height(48.dp))

                    OutlinedTextAreaField(strOptText = strComment, 12, 300)

                    Spacer(modifier = Modifier.height(48.dp))

                    Box(modifier = Modifier
                        .height(48.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable {
							isPopupVisible.value = true
							 }
                        .background(btnColorResId),
                        contentAlignment = Alignment.Center) {
                        Text(
	                        text = submitBtnLabel,
	                        textAlign = TextAlign.Center,
	                        color = Colors.white,
	                        fontSize = 16.sp,
	                        modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

	                if(isPopupVisible.value) {

		                SelfDismissingPopup(
			                durationMillis = 2500,
			                message = Res.string.thank_you_for_your_submission.asState,
			                onDismiss = {
								onSubmit(Pair(ratingChosen.value, strComment.value)) })
	                }
	                Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Preview()
@Composable
fun RateAndSubmitTicketDialogPreview()
{
	//PreviewContextConfigurationEffect()
    RateAndSubmitTicketDialog(Res.string.sign_or_decline_completed_transportation.asState, "", Res.string.confirm_or_decline.asState, Colors.cl_e02020, { }, {})
}