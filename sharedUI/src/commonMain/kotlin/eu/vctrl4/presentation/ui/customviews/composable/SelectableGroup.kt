package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun RadioButtonsGroup(
    radioOptions: List<IdNameValueName> = listOf(),
    selectedOption:MutableState<IdNameValueName>,
    onSelectionChanged: (IdNameValueName) -> Unit
)
{
    //val (selectedOption: IdNameSimple, onOptionSelected: (IdNameSimple) -> Unit) = remember { mutableStateOf(radioOptions[0]) }

    Column(Modifier.selectableGroup()) {
        radioOptions.forEach { item ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .selectable(
                        selected = (item == selectedOption.value), onClick = { }, role = Role.RadioButton
                    ), verticalAlignment = Alignment.CenterVertically
            ) {

                IconToggleButton( checked = selectedOption.value == item,
                    onCheckedChange = { selectedOption.value = item
                        onSelectionChanged(item)})
                {
                    Icon(
	                    painter = painterResource(if (selectedOption.value == item) Res.drawable.ic_radio_blue_selected else Res.drawable.ic_radio_unselected),
	                    contentDescription = "Radio button icon",
	                    modifier = Modifier.size(32.dp),
	                    tint = Colors.cl_00549F)
                }

/*                RadioButton(
                    modifier = Modifier.size(32.dp),
                    selected = (item == selectedOption.value),
                    onClick = null, // null recommended for accessibility with screen readers
                    colors = RadioButtonColors(
                        selectedColor = colorResource(R.color.cl_00549F),
                        unselectedColor = colorResource(R.color.cl_d8),
                        disabledSelectedColor = colorResource(R.color.cl_d8),
                        disabledUnselectedColor = colorResource(R.color.cl_d8)
                    )
                )*/

                Text(
                    text = item.valueName ?: "",
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.bodyLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}