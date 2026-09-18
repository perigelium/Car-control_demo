package eu.vctrl4.business.core

sealed class DataStateLocal<T> {

    data class Display<T>(val uiComponent: UIComponent) : DataStateLocal<T>()

    data class Data<T>(val data: T? = null, val status: Boolean? = null) : DataStateLocal<T>()

    data class Loading<T>(val progressBarState: ProgressBarState = ProgressBarState.Idle) :
        DataStateLocal<T>()


}