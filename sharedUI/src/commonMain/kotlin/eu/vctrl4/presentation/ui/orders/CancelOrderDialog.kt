package eu.vctrl4.presentation.ui.orders


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.TextAreaField
import eu.vctrl4.presentation.ui.customviews.composable.TopAppBarCustom
import eu.vctrl4.theme.*


@Composable
fun CancelOrderDialog(title: String?, onDismiss: () -> Unit, onSubmit: (String) -> Unit)
{
    val strComment = remember { mutableStateOf("") }

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(48.dp))

	                TextAreaField(strOptText = strComment, 10, 300)
                }

                Spacer(modifier = Modifier.height(48.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

                    Box(modifier = Modifier
                        .height(48.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable { onSubmit(strComment.value) }
                        .background(Colors.cl_e02020),
                        contentAlignment = Alignment.Center) {
                        Text(
	                        text = Res.string.cancel.asState,
	                        textAlign = TextAlign.Center,
	                        color = Colors.white,
	                        fontSize = 16.sp,
	                        modifier = Modifier.padding(horizontal = 24.dp)
                        )
                    }

                    //Spacer(modifier = Modifier.width(24.dp))

                    OutlinedButton(modifier = Modifier.height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = onDismiss,
                        border = BorderStroke(
                            1.dp, color = Colors.cl_CBCFD5),
                        content = {
                            Text(
	                            text = Res.string.do_not_cancel.asState, color = Colors.cl_232323, fontSize = 16.sp
                            )
                        })
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview()
@Composable
fun CancelOrderDialogPreview()
{
    CancelOrderDialog("Title\nSubtitle\nSubsubtitle", { }, {})
}