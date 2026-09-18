package eu.vctrl4.ui.custom_views.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.tooling.preview.*
import eu.vctrl4.business.core.*
import eu.vctrl4.presentation.component.*
import eu.vctrl4.storage.remote.chats.entities.*
import eu.vctrl4.theme.*
import eu.vctrl4.ui.base.*
import kotlinx.coroutines.flow.*

@Composable
fun DefaultScreenWrap(
    progressBarState: ProgressBarState,
    errors: Flow<UIComponent> = MutableSharedFlow(),
    screenContent: @Composable () -> Unit,
)
{
    val errorQueue = remember {
        mutableStateOf<Queue<UIComponent>>(Queue(mutableListOf()))
    }

    LaunchedEffect(errors) {
        errors.collect { errors ->
            errorQueue.appendToMessageQueue(errors)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(), shape = RectangleShape, Colors.cl_fafafa
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            //
            // process the queue
            if (!errorQueue.value.isEmpty())
            {
                errorQueue.value.peek()?.let { uiComponent ->

                    if (uiComponent is UIComponent.DialogTitleText)
                    {
                        AppTheme {
                            GenericDialog(modifier = Modifier, title = uiComponent.title, description = uiComponent.description,
                                onRemoveHeadFromQueue = { errorQueue.removeHeadMessage() })
                        }
                    }

                    if (uiComponent is UIComponent.DialogMsg)
                    {
                        AppTheme {
                            GenericDialog(modifier = Modifier, title = uiComponent.alert.title, description = uiComponent.alert.message,
                                onRemoveHeadFromQueue = { errorQueue.removeHeadMessage() })
                        }
                    }

                    if (uiComponent is UIComponent.Toast)
                    {
                            SelfDismissingPopup(
                                message = uiComponent.message,
                                onDismiss = {  errorQueue.removeHeadMessage() }
                            )
                    }
                }
            }

            if (progressBarState is ProgressBarState.Loading)
            {
                CircularProgressBarDialog()
            }
            screenContent()
        }
    }
}

@Preview
@Composable
fun ChatListScreenPreview()
{
    val chatListItem1 = ChatListItem()
    chatListItem1.Name = "1"
    val chatListItem2 = ChatListItem()
    chatListItem2.Name = "2"
    DefaultScreenWrap(
        errors = TODO(),
        screenContent = TODO(),
        progressBarState = TODO(),
    )
}