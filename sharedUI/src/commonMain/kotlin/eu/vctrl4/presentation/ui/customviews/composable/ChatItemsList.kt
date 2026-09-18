package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import eu.vctrl4.storage.remote.chats.entities.*
import eu.vctrl4.theme.*

/*@Composable
fun CircleAndTwoStringsRowList(stringsPairs: List<Pair<String, Int>>, itemColors: List<Int>, onItemSelected: (Int) -> Unit)
{
    Column(
        horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        stringsPairs.forEachIndexed() { index, pair ->

            val pairStr = Pair(pair.first, "${pair.second} psc.")

	        CircleAndTwoStringsRowItem(
		        titleSubtitle = pairStr,
		        itemColors[index],
		        { onItemSelected(index) })

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}*/

@Composable
fun ChatItemsList(titles: List<ChatListItem>, modifier: Modifier = Modifier, onItemSelected: (ChatListItem) -> Unit)
{
    Surface(modifier = modifier.fillMaxWidth(), shape = RectangleShape, color = Colors.transparent) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth(fraction = 0.95f)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            titles.forEach { item ->

                if (item.Name?.isNotBlank() == true)
                {
                    StringsRowItem(title = item.Name!!, onItemSelected = { onItemSelected(item) })

                    //Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun StringsRowItem(title: String, onItemSelected: () -> Unit)
{
    Surface(
        modifier = Modifier
            .padding(all = 4.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = 48.dp)
            .clickable { onItemSelected() },
        shape = RoundedCornerShape(12.dp),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 14.sp, color = Colors.cl_8e8e93)
        }
    }
}

@Composable
fun StringsListDialog(
    titles: List<String>, modifier: Modifier = Modifier, onItemSelected: (String) -> Unit, onDismiss: () -> Unit
)
{
    val shouldShowDialog = remember { mutableStateOf(true) }
	val scrollState = rememberScrollState()

    if (shouldShowDialog.value)
    {
        Dialog(properties = DialogProperties(usePlatformDefaultWidth = true), onDismissRequest = {
            shouldShowDialog.value = false
            onDismiss()
        }) {
            Surface(modifier = modifier.fillMaxWidth().verticalScroll(scrollState), shape = RoundedCornerShape(18.dp), color = Colors.cl_f5f5f5) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxWidth(fraction = 0.95f)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    titles.forEach { item ->

                        if (item.isNotBlank())
                        {
                            StringsRowItem(title = item, onItemSelected = { onItemSelected(item) })

                            //Spacer(modifier = Modifier.height(8.dp))
                        }
                    }

	                Spacer(modifier = Modifier.height(16.dp))

	                TextButton(onClick = { onDismiss() },
	                           colors = ButtonDefaults.buttonColors(contentColor = Colors.cl_00549F,        
	                                                                containerColor = Color.White)      
	                          ) { Text(Res.string.close.asState, fontSize = 16.sp) }

	                Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview()
@Composable
fun ChatItemsPreview()
{
    val listItem = ChatListItem()
    listItem.Name = "By order number 1"
    val listItem1 = ChatListItem()
    listItem1.Name = "By order number 2"

	StringsListDialog(titles = listOf("sdf", "listItem1"), Modifier, {}, {})
}

