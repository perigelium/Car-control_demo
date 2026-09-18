package eu.vctrl4.presentation.ui.appstart

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.customviews.composable.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*
import kotlin.random.*

@Composable
fun WelcomeScreen(onLanguageChanged: (String) -> Unit, onSubmit: () -> Unit) {

	fun randomBackgroundImage(): DrawableResource? {
		val randomInt = Random.nextInt(0, 2)

		return when (randomInt) {
			0 -> Res.drawable.ic_logo_lorry
			1 -> Res.drawable.ic_logo_tractor
			else -> null
		}
	}

	Surface(modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_b3b3b3) {

		Box(
			modifier = Modifier.fillMaxSize()
		   ) {
			val backgroundImageDrawable = randomBackgroundImage()

			backgroundImageDrawable?.let {
				Image(
					painter = painterResource(it),
					modifier = Modifier.fillMaxSize(),
					contentDescription = "login background",
					contentScale = ContentScale.FillBounds
				     )
			}

			Column(
				modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
					.padding(horizontal = 32.dp), horizontalAlignment = Alignment.Start
			      ) {

				LanguageSelector(
					modifier = Modifier.fillMaxWidth().padding(top = 52.dp),
					{onLanguageChanged(it)})

				Spacer(modifier = Modifier.weight(1f))

				Text(
					text = Res.string.restrans.asState,
					textAlign = TextAlign.Start,
					color = Colors.white,
					fontSize = 28.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.fillMaxWidth()
				    )

				Spacer(modifier = Modifier.height(16.dp))

				val fontFamily = FontFamily(
					Font(Res.font.pfbeausanspro_bold, FontWeight.Normal),
				                           )

				Text(
					text = Res.string.car_control.asState,
					textAlign = TextAlign.Start,
					color = Colors.white,
					fontSize = 40.sp,
					fontFamily = fontFamily,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.fillMaxWidth()
				    )

				Spacer(modifier = Modifier.height(40.dp))

				Box(
					modifier = Modifier.fillMaxWidth().height(56.dp)
						.clip(shape = RoundedCornerShape(12.dp)).clickable {
							onSubmit()
						}, contentAlignment = Alignment.Center
				   ) {
					Image(
						painter = painterResource(Res.drawable.btn_start_blue),
						modifier = Modifier.fillMaxSize(),
						contentScale = ContentScale.FillBounds,
						contentDescription = ""
					     )
					Text(
						text = Res.string.authorization.asState,
						textAlign = TextAlign.Center,
						color = Colors.white,
						fontSize = 14.sp,
						letterSpacing = 0.11.sp
					    )
				}
				Spacer(modifier = Modifier.height(40.dp))
			}
		}
	}
}

@Preview
@Composable
fun LoginScreenPreview() {
	WelcomeScreen(onLanguageChanged = {},
		onSubmit = {},
	             )
}