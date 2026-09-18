package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.presentation.ui.base.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun BottomAppBarWithFilterAndSearch(
    onFilterClick: () -> Unit,
    isSearchEnabled: MutableState<Boolean>,
    //scrollBehavior: BottomAppBarScrollBehavior
)
{
    BottomAppBar(
	    actions = {
            IconButton(onClick = { onFilterClick() }) {
                Image(
	                painter = painterResource(Res.drawable.ic_filter),
	                contentDescription = "Filter",
	                modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = {
                isSearchEnabled.value = !isSearchEnabled.value }) {
                Image(
	                painter = painterResource(Res.drawable.ic_search_ticket),
	                contentDescription = "Filter",
	                modifier = Modifier.size(24.dp)
                )
            }
        },
	    modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .height(56.dp),
	    containerColor = Colors.cl_f5f5f5,
        //scrollBehavior = scrollBehavior
    )
}

@Composable
fun BottomAppBarOrderDetails(
	items:List<NavigationItem>,
	remSelItemIndex: MutableIntState,
	onNavigationItemClick: (String) -> Unit
)
{
    NavigationBar(containerColor = Colors.cl_f5f5f5) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                modifier = Modifier.size(52.dp),
                selected = remSelItemIndex.intValue == index,
                onClick = {
                    remSelItemIndex.intValue = index
                    onNavigationItemClick(item.name)
                },
                icon = {
                    Icon(
                        painter = painterResource(item.unselectedIconRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(selectedIconColor = Colors.cl_69BE28, unselectedIconColor = Colors.cl_00549F, indicatorColor = Colors.cl_E5E5E5)
            )
        }
    }
}