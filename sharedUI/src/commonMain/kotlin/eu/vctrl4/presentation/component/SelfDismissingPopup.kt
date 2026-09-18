package eu.vctrl4.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import kotlinx.coroutines.*


@Composable
fun SelfDismissingPopup(
    message: String,
    durationMillis: Long = 3500L,
    onDismiss: () -> Unit
) {
    var isPopupVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        onDismiss()
        isPopupVisible = false
    }

    if(isPopupVisible)
    {
        Popup(
            alignment = Alignment.BottomCenter, onDismissRequest = onDismiss  
        ) {
            Surface(
                modifier = Modifier.padding(16.dp),
                color = Color.Gray.copy(alpha = 0.8f),
                shape = RoundedCornerShape(24.dp),
            ) {
                Text(
                    text = message, color = Color.White, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}