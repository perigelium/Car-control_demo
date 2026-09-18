package eu.vctrl4.business.core

import androidx.compose.runtime.*
import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.*

/*
What is the difference between StateFlow — SharedFlow — Channel?
With the shared flow, events are broadcast to an unknown number (zero or more) of subscribers.
In the absence of a subscriber, any posted event is immediately dropped.
It is a design pattern to use for events that must be processed immediately or not at all.

With the channel, each event is delivered to a single subscriber.
An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full,
waiting for a subscriber to appear. Posted events are never dropped by default.
* */

abstract class BaseViewModel<Event : ViewEvent,
        UiState : ViewState,
        SingleAction : ViewSingleAction> : ViewModel() {

    abstract fun setInitialState(): UiState
    abstract fun onTriggerEvent(event: Event)

    private val initialState: UiState by lazy { setInitialState() }
    private val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state = _state

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _action: Channel<SingleAction> = Channel()
    val action = _action.receiveAsFlow()


    private val _errors: Channel<UIComponent> = Channel()
    val errors = _errors.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                onTriggerEvent(it)
            }
        }
    }

    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    protected fun setAction(builder: () -> SingleAction) {
        val effectValue = builder()
        viewModelScope.launch { _action.send(effectValue) }
    }

    protected fun display(builder: () -> UIComponent) {
        val effectValue = builder()
        viewModelScope.launch {
            _errors.send(effectValue)
        }
    }


    fun <T> executeUseCase(
        flow: Flow<DataState<T>>,
        onSuccess: (T?) -> Unit,
        onLoading: (ProgressBarState) -> Unit,
        onNetworkStatus: (NetworkState) -> Unit = { }
    ) {
        viewModelScope.launch {
            flow.collectLatest { dataState ->
                when (dataState) {
                    is DataState.NetworkStatus -> onNetworkStatus(dataState.networkState)
                    is DataState.Response -> display { dataState.uiComponent }
                    is DataState.Data -> onSuccess(dataState.data)
                    is DataState.Loading -> onLoading(dataState.progressBarState)
                }
            }
        }
    }

    fun <T> executeUseCaseLocal(
        flow: Flow<DataStateLocal<T>>,
        onSuccess: (T?) -> Unit,
        onLoading: (ProgressBarState) -> Unit,
    ) {
        viewModelScope.launch {
            flow.collectLatest { dataState ->
                when (dataState) {
                    is DataStateLocal.Display -> display { dataState.uiComponent }
                    is DataStateLocal.Data -> onSuccess(dataState.data)
                    is DataStateLocal.Loading -> onLoading(dataState.progressBarState)
                }
            }
        }
    }
}