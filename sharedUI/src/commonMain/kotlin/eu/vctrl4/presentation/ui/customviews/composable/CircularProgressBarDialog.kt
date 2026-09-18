package eu.vctrl4.ui.custom_views.composable

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun CircularProgressBarDialog(
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000 // Speed of one full rotation
) {
    val infiniteTransition = rememberInfiniteTransition(label = "RotationTransition")

    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "RotationAngle"
    )

    Dialog(properties = DialogProperties(usePlatformDefaultWidth = false, dismissOnClickOutside = false), onDismissRequest = {}) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = RectangleShape,
            color = Colors.cl_black_one_third_transparent
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
	                painter = painterResource(Res.drawable.ic_gear_dark),
	                contentDescription = "Circular progress indicator",
	                modifier = modifier.rotate(rotationAngle).size(64.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CustomCircularProgressBarPreview()
{
    CircularProgressBarDialog()
}