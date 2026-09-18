package eu.vctrl4.presentation.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import eu.vctrl4.theme.*

@Composable
fun TopAppBarNavDrawer(title:String, itemsTotalLine: String, onMenuBtnClick: () -> Unit )
{
    Column(
	    modifier = Modifier.fillMaxWidth().height(84.dp).background(color = Colors.cl_00549F),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                onMenuBtnClick()
            }, content = {
                Icon(
	                Icons.Default.Menu, contentDescription = "", modifier = Modifier.size(32.dp), tint = Color.White
                )
            })

            Text(
	            text = title,
	            modifier = Modifier.padding(top = 4.dp),
	            color = Colors.cl_white,
	            fontSize = 18.sp,
            )

            Box(Modifier.size(48.dp))
        }
        Text(
	        text = itemsTotalLine,
	        modifier = Modifier.padding(start = 16.dp, top = 8.dp),
	        textAlign = TextAlign.Start,
	        color = Colors.cl_white,
	        fontSize = 12.sp,
        )
    }
}