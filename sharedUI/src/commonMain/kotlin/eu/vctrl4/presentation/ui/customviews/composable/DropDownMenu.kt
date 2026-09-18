package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuBox(
	items: List<Any>,
	hint: String? = null,
	modifier: Modifier,
	backColor: Color,
	textColor: Color,
	trailingIconColor: Color,
	selectedItem: MutableState<String?>,
	onItemClick: (Any) -> Unit
                   ) {
	var expanded by remember { mutableStateOf(false) } //val selectedItem: MutableState<Any?> = remember { mutableStateOf(if (items.isNotEmpty() && selectedItemIndex.value>=0) items[selectedItemIndex.value] else null) }

	Box(
		modifier = modifier.fillMaxWidth().background(backColor, shape = RoundedCornerShape(18.dp))
	   ) {
		MaterialTheme(shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(18.dp))) {
			ExposedDropdownMenuBox(
				modifier = Modifier.align(Alignment.CenterEnd)
					.background(backColor, shape = RoundedCornerShape(18.dp)),
				expanded = expanded,
				onExpandedChange = {
					expanded = !expanded
				}) {
				OutlinedTextField(
					value = selectedItem.value ?: Res.string.all.asState,
					onValueChange = {},
					readOnly = true,
					textStyle = LocalTextStyle.current.copy(
						textAlign = TextAlign.Center, fontSize = 12.sp
					                                       ),
					trailingIcon = {
						TrailingIcon(expanded = expanded, modifier = Modifier.size(32.dp))
					},
					modifier = Modifier.fillMaxWidth()
						.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
						.background(backColor),
					shape = RoundedCornerShape(18.dp),
					colors = OutlinedTextFieldDefaults.colors(
						focusedBorderColor = Colors.cl_b3b3b3,
						unfocusedBorderColor = Colors.cl_b3b3b3,
						focusedTextColor = textColor,
						unfocusedTextColor = textColor,
						unfocusedContainerColor = backColor,
						focusedContainerColor = backColor,
						focusedTrailingIconColor = trailingIconColor,
						unfocusedTrailingIconColor = trailingIconColor
					                                         )
				                 )

				ExposedDropdownMenu(
					modifier = Modifier.background(backColor),
					expanded = expanded,
					shape = RoundedCornerShape(0.dp, 0.dp, 18.dp, 18.dp),
					onDismissRequest = { expanded = false }) {
					items.forEach { item ->

						DropdownMenuItem(text = {
							Text(
								text = item.toString(), fontSize = 12.sp, color = textColor
							    )
						}, onClick = {
							expanded = false
							selectedItem.value = item.toString()
							onItemClick(item)
						})
						HorizontalDivider(color = Colors.cl_b3b3b3, thickness = 1.dp)
					}
				}
			}
		}
	}
}

@Preview
@Composable
fun DropdownMenuBoxPreview() { //val items = listOf(IdNameSimple(1, "first"), "second")


	//DropdownMenuBox(items, "Select...", Modifier, R.color.white, R.color.cl_232323, R.color.cl_232323, { })
}