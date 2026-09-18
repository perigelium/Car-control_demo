package eu.vctrl4.business.datasource.network.main.requests

import kotlinx.serialization.*

@Serializable
class ApiRequestBodyKtor<T>(  
	private val dt: String, // API subscriber name,
	private val from: String,  
	private val mn: String, // Message type,
	internal val type: String,  

	@Contextual
    private val body: T? = null,

	private val sign: String //  
)
{
    var template: String? = null
}