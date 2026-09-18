package eu.vctrl4.presentation.ui.appstart.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.appstart.login.view_model.*
import eu.vctrl4.theme.*
import kotlinx.coroutines.*
import org.jetbrains.compose.resources.*

@Composable
fun LoginScreen(
    state: LoginViewState,
    enableSubmitBtn: Boolean = true,
    resetFields: Boolean,
    onSubmit: (Pair<String?, String?>) -> Unit,
    onPrivacyPolicyClicked: () -> Unit
)
{
    val focusManager = LocalFocusManager.current
    val login = remember { mutableStateOf(state.strLogin) }
    val password = remember { mutableStateOf(state.strPasswd) }

    if(SessionVars.isAutoLogin)
    {
        LaunchedEffect(key1 = Unit) {
            delay(500)
            if (!state.strLogin.isNullOrBlank() && !state.strPasswd.isNullOrBlank())
            {
                onSubmit(Pair(login.value, password.value))
            }
        }
    }

    LaunchedEffect(key1 = resetFields) {
        if (resetFields)
        {
            login.value = ""
            password.value = ""
        }
    }

    val keyboardOptionsTextLogin =
        KeyboardOptions(autoCorrectEnabled = false, keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)

    val keyboardOptionsTextPassword =
        KeyboardOptions(autoCorrectEnabled = false, keyboardType = KeyboardType.Password, imeAction = ImeAction.Go)

    val keyboardActionsPassword = KeyboardActions(onGo = {
        focusManager.clearFocus(true)
        onSubmit(Pair(login.value, password.value))
    })

    Surface(modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_b3b3b3) {

        Box(
            modifier = Modifier.background(
                brush = Brush.linearGradient(
	                colors = listOf(Colors.cl_99162228, Colors.cl_00549F),
	                start = Offset(0f, Float.POSITIVE_INFINITY),
	                end = Offset.Zero
                ), shape = RectangleShape
            )
        ) {

            Image(
	            painter = painterResource(Res.drawable.login_background),
	            modifier = Modifier.fillMaxSize(),
	            contentDescription = "login background",
	            contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.weight(2f))

	            Text(
		            text = Res.string.authorization.asState,
		            textAlign = TextAlign.Center,
		            color = Colors.white,
		            fontSize = 22.sp,
		            modifier = Modifier.fillMaxWidth()
	                )

                Spacer(modifier = Modifier.height(56.dp))

                Text(
	                text = Res.string.login.asState,
	                textAlign = TextAlign.Start,
	                color = Colors.white,
	                fontSize = 14.sp,
	                modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

	            PasswordTextField(
		            hint = "",
		            value = login.value ?: "",
		            isPasswordType = false,
		            keyboardOptions = keyboardOptionsTextLogin,
		            resetField = resetFields,
		            onValueChange = {
			            login.value = it.take(25)
		            })

                Spacer(modifier = Modifier.height(16.dp))

                Text(
	                text = Res.string.password.asState,
	                textAlign = TextAlign.Start,
	                color = Colors.white,
	                fontSize = 14.sp,
	                modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

	            PasswordTextField(
		            hint = "",
		            isPasswordType = true,
		            value = password.value ?: "",
		            keyboardOptions = keyboardOptionsTextPassword,
		            keyboardActions = keyboardActionsPassword,
		            resetField = resetFields,
		            onValueChange = { password.value = it.take(25) })

                Spacer(modifier = Modifier.height(64.dp))

                Box(
	                modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .clickable(enabled = enableSubmitBtn) { //coroutineScope.launch { focusManager.clearFocus(true) }
                            focusManager.clearFocus(true)
                            onSubmit(Pair(login.value, password.value))
                        }
                        .background(Colors.cl_69BE28), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(Res.drawable.background_login_btn),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = "login btn background",
                        contentScale = ContentScale.FillBounds
                    )
                    Text(
	                    text = Res.string.login_action.asState, textAlign = TextAlign.Center, color = Colors.white, fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
	                text = Res.string.privacy_policy.asState,
	                textAlign = TextAlign.Center,
	                color = Colors.white,
	                fontSize = 12.sp,
	                textDecoration = TextDecoration.Underline,
	                fontStyle = FontStyle.Italic,
	                modifier = Modifier.clickable(onClick = { onPrivacyPolicyClicked() })
                )

                //Box(modifier = Modifier.background(color = Color.White, shape = RectangleShape).height(3.dp).weight(1f))

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview()
{
    LoginScreen(
        state = LoginViewState(),
        true, false,
        onSubmit = { Pair("", "") },
        onPrivacyPolicyClicked = {},
    )
}