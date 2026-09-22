package eu.vctrl4.presentation.ui.base

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.core.ViewState
import eu.vctrl4.common.*
import eu.vctrl4.presentation.component.*
import eu.vctrl4.theme.*
import kotlinx.coroutines.flow.*


@Composable
fun DefaultScreenUI(
	state: ViewState,
    //errors: Flow<UIComponent> = MutableSharedFlow(),
    //progressBarState: ProgressBarState = ProgressBarState.Idle,
	networkState: NetworkState = NetworkState.Good,
	onTryAgain: () -> Unit = {},
	screenContent: @Composable () -> Unit,
) {

    val errorQueue = remember {
        mutableStateOf<Queue<UIComponent>>(Queue(mutableListOf()))
    }

    Scaffold(
        topBar = {}
    ) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            screenContent()


/*            LaunchedEffect(errors) {
                errors.collect { errors ->
                    errorQueue.appendToMessageQueue(errors)
                }
            }*/


            // process the queue
            if (!errorQueue.value.isEmpty()) {
                errorQueue.value.peek()?.let { uiComponent ->
                    if (uiComponent is UIComponent.DialogMsg) {
                        CreateUIComponentDialog(
                            title = uiComponent.alert.title,
                            description = uiComponent.alert.message,
                            onRemoveHeadFromQueue = { errorQueue.removeHeadMessage() }
                        )
                    }
                    if (uiComponent is UIComponent.DialogTitleText) {

                        ShowSnackBar(
                            title = uiComponent.title,
                            onDismiss = { errorQueue.removeHeadMessage() },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }

/*            if (networkState == NetworkState.Failed && progressBarState == ProgressBarState.Idle) {
                FailedNetworkScreen(onTryAgain = onTryAgain)
            }*/

/*            if (progressBarState is ProgressBarState.LoadingWithLogo) {
                LoadingWithLogoDialog()
            }*/

/*            if (state == ViewState.Loading) {

                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    strokeWidth = 2.dp,
                    color = Colors.cl_00549F //if (enabled) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.primary,
                )
            }*/
        }
    }
}


fun MutableState<Queue<UIComponent>>.appendToMessageQueue(uiComponent: UIComponent) {
    if (uiComponent is UIComponent.None) {
        //println("$CUSTOM_TAG appendToMessageQueue:  ${uiComponent.message}")
        return
    }

    val queue = this.value
    queue.add(uiComponent)

    this.value = Queue(mutableListOf()) // force to recompose
    this.value = queue
}

fun MutableState<Queue<UIComponent>>.removeHeadMessage() {
    if (this.value.isEmpty()) {
        //println("$CUSTOM_TAG: removeHeadMessage: Nothing to remove from DialogQueue")
        return
    }

    val queue = this.value
    queue.remove() // can throw exception if empty
    this.value = Queue(mutableListOf()) // force to recompose
    this.value = queue
}


@Composable
fun <Effect : ViewSingleAction> EffectHandler(
    effectFlow: Flow<Effect>,
    onHandleEffect: (Effect) -> Unit
) {
    LaunchedEffect(Unit) {
        effectFlow.collect { effect ->
            onHandleEffect(effect)
        }
    }
}

@Composable
fun FailedNetworkScreen(onTryAgain: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        //Image(painterResource(Res.drawable.no_wifi), null)

        Spacer(modifier = Modifier.size(32.dp))
        Text(
	        text = Res.string.error_check_internet_connection.asState,
	        style = MaterialTheme.typography.labelMedium,
	        textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.size(16.dp))

/*        DefaultButton(
            text = "Try Again",
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    DEFAULT__BUTTON_SIZE
                )
        ) {
            onTryAgain()
        }*/


    }

}

@Composable
fun LoadingWithLogoDialog() {
    Dialog(properties = DialogProperties(usePlatformDefaultWidth = true, dismissOnClickOutside = true, dismissOnBackPress = true),
        onDismissRequest = {}) {
        Surface(shape = RoundedCornerShape(12.dp), color = Colors.white, content= {
            Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                CircularProgressIndicator(modifier = Modifier.size(36.dp),
                    strokeWidth = 4.dp,
                    color = Colors.cl_00549F)

/*                Spacer(modifier = Modifier.width(24.dp))

                Text(text = stringResource(R.string.DownloadingDataPleaseWait),
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center)*/
            }
        })
    }
}

@Preview
@Composable
fun DefaultScreenUIpreview()
{
    LoadingWithLogoDialog()
}













