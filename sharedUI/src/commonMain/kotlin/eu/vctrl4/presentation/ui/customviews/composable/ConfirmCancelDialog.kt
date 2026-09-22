package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*

@Composable
fun ConfirmCancelDialog(dialogTitle: String,
                        strMsg: String,
                        submitBtnlabel: String = Res.string.Ok.asState,
                        onSubmit: () -> Unit,
                        dismissBtnlabel: String = Res.string.cancel.asState,
                        onDismiss: () -> Unit)
{
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = true, dismissOnClickOutside = true, dismissOnBackPress = true),
        onDismissRequest = onDismiss) {
        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), Colors.cl_f5f5f5) {

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                , horizontalAlignment = Alignment.CenterHorizontally) {

                Spacer(modifier = Modifier.height(24.dp))
                
                Text(text = dialogTitle, style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = strMsg, Modifier.padding(), style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {

                    TextButton(onClick = { onSubmit() },
                        colors = ButtonDefaults.buttonColors(contentColor = Colors.cl_00549F,        
                                                             containerColor = Color.White)      
                    ) { Text(submitBtnlabel, fontSize = 16.sp) }

                    Spacer(modifier = Modifier.width(16.dp))

                    TextButton(onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(contentColor = Colors.cl_00549F,        
                                                             containerColor = Color.White)      
                    ) { Text(dismissBtnlabel, fontSize = 16.sp) }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Preview()
@Composable
fun ConfirmCancelDialogPreview()
{
    ConfirmCancelDialog(dialogTitle = "ConfirmCancel", strMsg = "MyText", submitBtnlabel = "Ok", {}, Res.string.cancel.asState,{})
}