package eu.vctrl4.business.core

sealed class NetworkState{

   data object Good: NetworkState()
   data object Failed: NetworkState()

}
