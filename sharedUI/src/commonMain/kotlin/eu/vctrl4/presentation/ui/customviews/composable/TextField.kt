package eu.vctrl4.presentation.ui.customviews.composable

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
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun TextAreaField(strOptText: MutableState<String>, maxLines: Int, maxChars: Int)
{
    TextField(
	    value = strOptText.value,
	    onValueChange = { txt -> if (txt.length <= maxChars && txt.lines().size <= maxLines) strOptText.value = txt },
	    label = { Text(Res.string.your_comment.asState) },
	    colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Colors.cl_f5f5f5,
	    unfocusedIndicatorColor = Colors.cl_f5f5f5,
            focusedLabelColor = Colors.cl_f5f5f5,
            unfocusedContainerColor = Colors.cl_f5f5f5,
            focusedContainerColor = Colors.cl_f5f5f5
             ),
        supportingText = {
            Text(
                text = "${strOptText.value.length} / $maxChars",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        },
        maxLines = maxLines
    )
}

@Composable
fun CustomTextField(
    strValue: String,
    strHint: String? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    trailingIconResId: DrawableResource? = null,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    onRightIconClick: () -> Unit = {},
)
{
    var text by remember { mutableStateOf(strValue) }

    OutlinedTextField(
        maxLines = 99,
        singleLine = true,
        value = strValue,
        readOnly = readOnly,
        onValueChange = {
            text = it
            onValueChange(text)
        },
        label = {
            Text(strHint?:"")
        },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            trailingIconResId?.let {
                Image(
                    painter = painterResource( it),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Colors.cl_00549F),
                    modifier = Modifier.size(32.dp).clickable {
                        onRightIconClick() }
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Colors.cl_d7d6d6,
            unfocusedIndicatorColor = Colors.cl_d7d6d6,
            unfocusedLabelColor = Colors.cl_232323,
            focusedLabelColor = Colors.cl_232323,
            unfocusedTextColor = Colors.cl_232323,
            unfocusedContainerColor = Colors.cl_white,
            focusedContainerColor = Colors.cl_white
        )
    )

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun OutlinedTextAreaField(strOptText: MutableState<String>, maxLines: Int, maxChars: Int)
{
    OutlinedTextField(
        value = strOptText.value,
        onValueChange = { txt -> if (txt.length <= maxChars && txt.lines().size <= maxLines) strOptText.value = txt },
        label = { Text(Res.string.your_comment.asState) },
        minLines = 5,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Colors.cl_d7d6d6,
            unfocusedIndicatorColor = Colors.cl_d7d6d6,
            unfocusedLabelColor = Colors.cl_232323,
            focusedLabelColor = Colors.cl_232323,
            unfocusedTextColor = Colors.cl_232323,
            unfocusedContainerColor = Colors.cl_white,
            focusedContainerColor = Colors.cl_white
        ),

        supportingText = {
            Text(
	            text = "${strOptText.value.length} / $maxChars",
	            modifier = Modifier.fillMaxWidth(),
	            textAlign = TextAlign.End,
            )
        },
        maxLines = maxLines
    )
}


@Preview()
@Composable
fun OutlinedTextAreaTextPreview()
{
    val strComment = remember { mutableStateOf("") }
    val keyboardOptions = KeyboardOptions(autoCorrectEnabled = false, keyboardType = KeyboardType.Uri, imeAction = ImeAction.Done)

    //OutlinedTextAreaField(strOptText = strComment, 12, 300)

    CustomTextField(
        strValue = strComment.value,
        strHint = Res.string.select_date.asState,
        readOnly = false,
        trailingIconResId = Res.drawable.icon_calendar)
}