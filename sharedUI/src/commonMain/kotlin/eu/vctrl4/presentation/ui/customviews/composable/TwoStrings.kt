package eu.vctrl4.presentation.ui.customviews.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun TwoSideStringsRowItem(titleSubtitle: Pair<String, String?>, fullWidth: Boolean = true)
{
    val modifier = if (fullWidth) Modifier.fillMaxWidth() else Modifier

    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (titleSubtitle.second?.isNotBlank() == true)
        {
            Text(text = titleSubtitle.first, fontSize = 12.sp, color = Colors.cl_8e8e93)

            if (titleSubtitle.second?.isNotBlank() == true)
            {
                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = titleSubtitle.second!!, fontSize = 12.sp, color = Colors.cl_393854)
            }
        }
    }
}

@Composable
fun CircleAndTwoStringsRowItem(titleSubtitle: Pair<String, String>, itemColor: Color, onClicked: () -> Unit)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(shape = CircleShape)
                .background(itemColor)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.width(16.dp))

        val fontFamily = FontFamily(
            Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
        )

        Text(
            text = titleSubtitle.first,
            fontSize = 12.sp,
            fontFamily = fontFamily,
            color = Colors.cl_506074,
            modifier = Modifier.weight(.75f).align(Alignment.Bottom)
        )

        Spacer(
            modifier = Modifier
                .weight(.1f)
                .defaultMinSize(minWidth = 16.dp)
        )

        Text(
            text = titleSubtitle.second,
            fontSize = 12.sp,
            fontFamily = fontFamily,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(.25f).align(Alignment.Bottom),
            maxLines = 1
        )
    }
}

@Composable
fun TwoStringsColumnItem(titleSubtitle: IdNameValueName)
{
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(text = titleSubtitle.Name ?: "", fontSize = 12.sp, maxLines = 1, color = Colors.cl_8e8e93)

        Spacer(modifier = Modifier.height(8.dp))

        val fontFamily = FontFamily(
	        Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
        )

        Text(
            text = titleSubtitle.valueName ?: Res.string.no_data.asState,
            fontSize = 14.sp,
            color = Colors.cl_4c4c4c,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Thin
        )
    }
}

@Composable
fun ImgTitleTextRowItem(titleText: TitleTextAttrs)
{
    val fontFamily = FontFamily(
        Font(Res.font.pfbeausanspro_regular, FontWeight.Normal),
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
		// no usages !

/*        if (titleText.imgRes != null)
        {
            Image(
                painter = painterResource(titleText.imgRes), contentDescription = "", Modifier.size(16.dp)
            )
        }*/

        if (titleText.title?.isNotBlank() == true)
        {
            Text(
                text = titleText.title,
                fontSize = 12.sp,
                color = Colors.cl_8e8e93,
                modifier = Modifier.defaultMinSize(minWidth = titleText.titleTextWidthBetween.dp),
                fontFamily = fontFamily
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = titleText.strText, fontSize = 12.sp, color = Colors.cl_393854, fontFamily = fontFamily
        )
    }
}

@Preview()
@Composable
fun TwoStringsRowPreview()
{
    CircleAndTwoStringsRowItem(Pair(Res.string.title.asState,"9999 psc"), Colors.cl_00549F, {})
}