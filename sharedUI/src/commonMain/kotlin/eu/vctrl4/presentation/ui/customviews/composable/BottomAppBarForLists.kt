package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun BottomAppBarForLists(isSearchEnabled: MutableState<Boolean>, isFilterEnabled: Boolean, onFilterClick: () -> Unit)
{
    BottomAppBar(actions = {
        if(isFilterEnabled)
        {
            IconButton(onClick = { onFilterClick() }) {
                Image(
	                painter = painterResource(Res.drawable.ic_filter),
	                contentDescription = "Filter",
	                modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { isSearchEnabled.value = !isSearchEnabled.value }) {
            Image(
	            painter = painterResource(Res.drawable.ic_search_ticket), contentDescription = "Filter", modifier = Modifier.size(24.dp)
            )
        }
    }, modifier = Modifier.padding(start = 8.dp, end = 8.dp).height(56.dp), containerColor = Colors.cl_f5f5f5)
}