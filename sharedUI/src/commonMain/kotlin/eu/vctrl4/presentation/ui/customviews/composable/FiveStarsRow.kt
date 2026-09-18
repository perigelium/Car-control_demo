package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.common.*
import org.jetbrains.compose.resources.*

@Composable
fun FiveStarsRow(rating: Int, rowWidth: Int = 0, imgSize: Int, onItemSelected: (Int) -> Unit, readOnly: Boolean = false)
{
    val items: MutableList<Int> = arrayListOf(0, 0, 0, 0, 0)

    val modifier = if (rowWidth == 0) Modifier.fillMaxWidth() else Modifier.width(rowWidth.dp)

    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {

        items.forEachIndexed { index, i ->

            val resourceId = if (index < rating) Res.drawable.ic_yellow_star else Res.drawable.ic_yellow_empty_star

            Image(painter = painterResource(resourceId), contentDescription = Res.string.rating.asState,
                Modifier
                    .size(imgSize.dp)
                    .clickable {
                        if (!readOnly)
                        {
                            onItemSelected(index + 1)
                        }
                    })
        }
    }
}

@Preview
@Composable
fun FiveStarsRowPreview()
{
    FiveStarsRow(3, 150, 24, onItemSelected = { })
}