package eu.vctrl4.presentation.ui.appstart.login

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*


@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    isPasswordType: Boolean,
    hint: String,
    value: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions = KeyboardActions(),
    readOnly: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    resetField: Boolean = false,
    onValueChange: (String) -> Unit,
)
{
    val isPasswordVisible = remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(value) }

    LaunchedEffect(key1 = enabled) {
        if (!enabled) isPasswordVisible.value = false
    }

    LaunchedEffect(key1 = resetField) {
        if(resetField) text = ""
    }

    OutlinedTextField(
	    isError = isError,
	    modifier = modifier.fillMaxWidth().defaultMinSize(minHeight = 52.dp).border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(12.dp)),
	    colors = TextFieldDefaults.colors(focusedIndicatorColor = Colors.transparent,
	                                      unfocusedIndicatorColor = Colors.transparent,
	                                      unfocusedLabelColor = Colors.transparent,
	                                      focusedLabelColor = Colors.transparent,
	                                      unfocusedTextColor = Colors.white,
	                                      focusedTextColor = Colors.white,
	                                      unfocusedContainerColor = Colors.transparent,
	                                      focusedContainerColor = Colors.transparent),
	    shape = RoundedCornerShape(12.dp),
	    readOnly = readOnly,
	    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 16.sp),
	    placeholder = { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center)
        { Text(text = hint, style = MaterialTheme.typography.titleMedium) } },
	    value = text,
	    onValueChange = {text = it
            onValueChange(it) },
	    trailingIcon = {
            if (isPasswordType)
            {
                IconButton(onClick = {
                    if (enabled)
                    {
                        isPasswordVisible.value = !isPasswordVisible.value
                    }
                }) {
                    when (isPasswordVisible.value)
                    {
                        false -> Icon(
	                        painter = painterResource(Res.drawable.ic_password_show),
	                        contentDescription = null,
	                        tint = Colors.white,
                        )

                        true -> Icon(painter = painterResource(Res.drawable.ic_password_hide),
                                     contentDescription = null,
                                     tint = Colors.white)
                    }
                }
            }
        },
	    keyboardOptions = keyboardOptions,
	    keyboardActions = keyboardActions,
	    visualTransformation = if (isPasswordType)
        {
            when (isPasswordVisible.value)
            {
                true -> VisualTransformation.None
                false -> PasswordVisualTransformation()
            }
        } else VisualTransformation.None,
	    maxLines = 1
    )
}

@Preview
@Composable
fun PasswordTextPreview()
{
    PasswordTextField(hint = "Your password", value = "", isPasswordType = true, keyboardOptions = KeyboardOptions.Default, onValueChange = {})
}
