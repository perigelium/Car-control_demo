package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
class ApiRequestEmptyBodyKtor(  
    private val dt: String, //API Subscriber name,
    private val from: String,  
    private val mn: String, //Message type,
    private val type: String,  

    private val sign: String
)
{
    var template: String? = null
}