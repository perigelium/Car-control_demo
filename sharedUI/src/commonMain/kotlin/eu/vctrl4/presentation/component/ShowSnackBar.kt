package eu.vctrl4.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*


@Composable
fun ShowSnackBar(
    modifier: Modifier, title: String, onDismiss: () -> Unit
)
{ // Show the Snackbar
    Snackbar(
	    modifier = modifier.padding(16.dp),
	    action = {
            Button(colors = ButtonDefaults.buttonColors(), onClick = { // Dismiss the Snackbar
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
	    shape = RoundedCornerShape(12.dp),
	    containerColor = Colors.cl_E5E5E5,
	    contentColor = Colors.cl_506074)
     {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
fun ShowSnackBarPreview()
{
    AppTheme {
        ShowSnackBar(modifier = Modifier, title = Res.string.error.asState, {})
    }
}