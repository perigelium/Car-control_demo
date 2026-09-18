package eu.vctrl4.storage.remote.entities

data class CompanyOrderTypeRequest
    (val CompanyId: String, val OrderType: String, val ShowTrailer:Boolean = true)


