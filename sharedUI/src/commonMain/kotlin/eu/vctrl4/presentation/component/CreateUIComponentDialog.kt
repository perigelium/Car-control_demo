package eu.vctrl4.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import eu.vctrl4.theme.Colors.cl_black_one_third_transparent


@Composable
fun CreateUIComponentDialog(
    title: String, description: String, onRemoveHeadFromQueue: () -> Unit
)
{
    GenericDialog(
        modifier = Modifier.fillMaxWidth(0.9f),
        title = title,
        description = description,
        onRemoveHeadFromQueue = onRemoveHeadFromQueue
    )
}

@Composable
fun GenericDialog(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onRemoveHeadFromQueue: () -> Unit,
)
{
    CustomAlertDialog(
        onDismissRequest = { onRemoveHeadFromQueue() }, modifier = modifier
    ) {
        Card( //colors = DefaultCardColorsTheme(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = title, modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = modifier.height(16.dp))
                Text(text = description)
                Spacer(modifier = modifier.height(16.dp))
                OutlinedButton(
                    onClick = onRemoveHeadFromQueue,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    border = BorderStroke(
                        1.dp, color = Colors.cl_blueDark),
                ) { Text(Res.string.understand.asState) }
            }
        }
    }
}

@Composable
fun DialogMsgScr(
    modifier: Modifier = Modifier,
    dlgMessage: String,
    description: String,
    onRemoveHeadFromQueue: () -> Unit,
)
{
    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = true, dismissOnClickOutside = true),
        onDismissRequest = { onRemoveHeadFromQueue() }) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = cl_black_one_third_transparent
        ) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(dlgMessage)
            }
        }
    }
}

@Preview
@Composable
fun DialogPreview()
{
    GenericDialog(
        modifier = Modifier, title = "String", description = "String", onRemoveHeadFromQueue = {})
}




