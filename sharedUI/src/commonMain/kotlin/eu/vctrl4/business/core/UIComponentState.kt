package eu.vctrl4.business.core

/**
 * A generic class for hiding/showing some ui component.
 */
sealed class UIComponentState {

   data object Show: UIComponentState()

   data object Hide: UIComponentState()
}
