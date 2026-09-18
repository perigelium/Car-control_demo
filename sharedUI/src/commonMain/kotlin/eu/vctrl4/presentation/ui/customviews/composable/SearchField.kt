package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.text.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import kotlinx.coroutines.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
	searchQuery: String,
	onQuerySubmitted: (String) -> Unit,
	modifier: Modifier = Modifier,
	searchHint:String = Res.string.search.asState,
	keyboardType: KeyboardType = KeyboardType.Unspecified
) {
    val strSearch:MutableState<String> = remember{ mutableStateOf(searchQuery) }
    val keyboardOptions = KeyboardOptions(autoCorrectEnabled = false, keyboardType = keyboardType, imeAction = ImeAction.Done)
    val keyboardActions = KeyboardActions(onDone = { onQuerySubmitted(strSearch.value)})

    val focus1st = remember { FocusRequester() }

    LaunchedEffect(key1 = Unit) {
        delay(250)
        focus1st.requestFocus()
    }

    OutlinedTextField(
        value = strSearch.value,
        onValueChange = {strSearch.value = it},
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier.focusRequester(focus1st)
            .fillMaxWidth()
/*            .clip(MaterialTheme.shapes.extraSmall)*/
            .indicatorLine(enabled = false,
                isError = false,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                focusedIndicatorLineThickness = 0.dp,
                unfocusedIndicatorLineThickness = 0.dp),
        placeholder = { Text(text = searchHint) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = ""
            )
        },
        trailingIcon = {
            Box (modifier = Modifier.size(48.dp).clickable { onQuerySubmitted(strSearch.value) }, contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.PlayArrow, contentDescription = "", tint = Colors.cl_00549F)
            }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xf5, 0xf5, 0xf5),
            focusedTextColor = Color.Black,
            unfocusedContainerColor = Color(0xf5, 0xf5, 0xf5),
            unfocusedTextColor = Color.Black
        )
    )
}

@Preview
@Composable
fun PreviewSearchField()
{
    SearchField(searchQuery = "", onQuerySubmitted = {})
}
