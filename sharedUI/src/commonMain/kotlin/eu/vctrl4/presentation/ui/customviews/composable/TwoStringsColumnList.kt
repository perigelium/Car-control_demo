package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import eu.vctrl4.business.datasource.storage.entities.*

@Composable
fun TwoStringsColumnList(titleTexts: List<IdNameValueName>)
{
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            titleTexts.forEach { nameValue ->

                TwoStringsColumnItem(titleSubtitle = nameValue)

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Preview()
@Composable
fun TwoStringsListDialogPreview()
{
    TwoStringsColumnList(titleTexts = listOf(IdNameValueName("Name", "Value"), IdNameValueName("Name_2", "Value_2")))
}