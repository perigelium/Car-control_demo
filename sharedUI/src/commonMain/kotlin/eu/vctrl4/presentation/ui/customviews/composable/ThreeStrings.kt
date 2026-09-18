package eu.vctrl4.ui.custom_views.composable


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.storage.entities.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun ThreeStringsRowItem(titleTexts: TitleTwoTextsAttrs)
{
    val fontFamily = FontFamily(
	    Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
    )

    if (titleTexts.strText1?.isNotBlank() == true || titleTexts.strText2?.isNotBlank() == true)
    {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            if (titleTexts.title?.isNotBlank() == true)
            {
                Text(
	                text = titleTexts.title,
	                fontSize = 12.sp,
	                color = Colors.cl_8e8e93,
	                modifier = Modifier.defaultMinSize(minWidth = titleTexts.titleTextWidthBetween.dp),
	                fontFamily = fontFamily
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = titleTexts.strText1 ?: "-",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Colors.cl_393854,
                fontFamily = fontFamily,
                modifier = Modifier.defaultMinSize(minWidth = titleTexts.text1Text2WidthBetween.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = titleTexts.strText2 ?: "-",
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                color = Colors.cl_393854,
                modifier = Modifier.defaultMinSize(minWidth = titleTexts.text1Text2WidthBetween.dp),
                fontFamily = fontFamily
            )
        }
    }
}