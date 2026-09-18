package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
class AuthRequest(
    private val UserName: String,
    private val Password: String,
    private val ShowProfile: Boolean,
    private val TokenMobile:String?
) 