package eu.vctrl4.business.datasource.network.main.requests

data class CompanyOrderTypeRequest
    (val CompanyId: String, val OrderType: String, val ShowTrailer:Boolean = true)


