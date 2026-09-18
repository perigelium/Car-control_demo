package eu.vctrl4.business.core

import eu.vctrl4.business.datasource.network.common.*

sealed class UIComponent {

    data class Toast(
        val message: String
    ): UIComponent()

    data class DialogMsg(
        val alert: JAlertResponse
    ): UIComponent()

    data class ToastSimple(
        val title:String,
    ): UIComponent()

    data class DialogTitleText(
        val title:String,
        val description:String
    ): UIComponent()

    data class None(
        val message:String,
    ): UIComponent()
}