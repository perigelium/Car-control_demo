package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
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
import eu.vctrl4.common.*
import eu.vctrl4.theme.*
import org.jetbrains.compose.resources.*

@Composable
fun TopAppBarCustom(
	backBtnTxt: String,
	onBackBtnClick: () -> Unit,
	titleTxt: String,
	backColorId: Color,
	showBottomLine: Boolean = false,
	rightBtn1ResourceId: DrawableResource? = null,
	onRightBtn1Click: () -> Unit = {},
	rightBtn2ResourceId: DrawableResource? = null,
	onRightBtn2Click: () -> Unit = {},
	titleColorId: Color = Colors.cl_232323,
	isBackBtnPresent: Boolean = true,
	height: Int = 56
)
{
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp)
            .background(color = backColorId),

        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackBtnClick) {
                val modifier = if (isBackBtnPresent) Modifier.alpha(255f) else Modifier.alpha(0f)

                Icon(
                    Icons.AutoMirrored.Default.ArrowBack, contentDescription = Res.string.back.asState, modifier, tint = Color.Gray
                )
            }

            Text(
                text = backBtnTxt,
                textAlign = TextAlign.Center,
                color = Colors.cl_00549F,
                fontSize = 14.sp,
                modifier = Modifier.clickable(enabled = true) { onBackBtnClick() })
        }

        Text(
            text = titleTxt,
            textAlign = TextAlign.Center,
            color = titleColorId,
            modifier = Modifier.weight(1f),
            fontSize = 17.sp,
            fontWeight = FontWeight(500)
        )

        if (rightBtn2ResourceId != null)
        {
            Box(
                Modifier
                    .size(48.dp)
                    .clickable { onRightBtn2Click() }, contentAlignment = Alignment.Center) {

                Image(painter = painterResource(rightBtn2ResourceId), contentDescription = "", Modifier.size(24.dp))
            }
        }

        Spacer(modifier = Modifier.width(4.dp))

        Box(
            Modifier
                .size(48.dp)
                .clickable { onRightBtn1Click() }, contentAlignment = Alignment.Center) {
            if (rightBtn1ResourceId != null)
            {
                Image(painter = painterResource(rightBtn1ResourceId), contentDescription = Res.string.info.asState, Modifier.size(24.dp))
            }
        }
    }

    if (showBottomLine)
    { // bottom 1dp light gray DD horizontal line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(color = Colors.cl_d8)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenteredAppBar(
    orderState:String,
    onBackBtnClick: () -> Unit,
    backColorId: Color,
    titleTxt: String,
    titleColorId: Color = Colors.cl_232323,
    isBackBtnPresent: Boolean = true,
    height: Int = 56,
    onActionInvoked: (String) -> Unit,
)
{
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var showMenu by remember { mutableStateOf(false) }
    val toolTipState = rememberTooltipState()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backColorId,
            titleContentColor = titleColorId,
        ),
        modifier = Modifier, expandedHeight = height.dp,
        title = {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.weight(1f, true),
                    text = titleTxt,
                    textAlign = TextAlign.Center,
                    color = titleColorId,
                    fontSize = 17.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(500),
                    maxLines = 1
                )
            }
        },
        windowInsets = WindowInsets(0, 0, 0, 0),
        navigationIcon = {
            Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackBtnClick) {
                    val modifier = if (isBackBtnPresent) Modifier.alpha(255f) else Modifier.alpha(0f)

                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = Res.string.back.asState,
                        modifier,
                        tint = Colors.white)
                }
            }
        },
        actions = {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {

                val isOrderCancelable = "CN" != orderState && "RD" != orderState // "NW" == orderState

                if(isOrderCancelable)
                {
                    Box(
                        Modifier.size(48.dp).clickable { showMenu = !showMenu }, contentAlignment = Alignment.Center) {

                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Overflow",
                            tint = Colors.white
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu, onDismissRequest = { showMenu = false }) {

                        if(isOrderCancelable)
                        {
                            DropdownMenuItem(
	                            onClick = {
                                    showMenu = false
                                    onActionInvoked("delete") },
	                            text = { Text(text = Res.string.cancel_order.asState) },
	                            leadingIcon = {
                                    Icon(painter = painterResource(Res.drawable.ic_delete_white_24dp), tint = Colors.cl_00549F, contentDescription = "")
                                })
                        }
                    }
                }
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Preview
@Composable
fun TopAppBarCustomPrevew()
{ //TopAppBarCustom("", { }, Res.string.title.asState, R.color.cl_f5f5f5, false, R.drawable.ic_info, {}, R.drawable.ic_stats, {}, isBackBtnPresent = true)

    CenteredAppBar(
        orderState = "NW",
        onBackBtnClick = {},
        backColorId = Colors.cl_00549F,
        titleTxt = Res.string.title.asState,
        titleColorId = Colors.white,
        onActionInvoked = {})

    /*        val msgEditTint = MutableLiveData<Int>()
            val msgDeleteTint = MutableLiveData<Int>()
            TopAppBarWithNavBtnAndDropDownMenu({}, {}, {}, {}, R.color.cl_f5f5f5, msgEditTint, msgDeleteTint)
            msgEditTint.setValue(R.color.cl_b3b3b3)
            msgDeleteTint.setValue(R.color.cl_b3b3b3)*/
}

