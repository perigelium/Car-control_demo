package eu.vctrl4.business.datasource.network.main.responses

import androidx.room.*
import eu.vctrl4.storage.remote.entities.*
import kotlinx.serialization.*


@Serializable
class Config()
{
    @ColumnInfo(index = true)
    var SystemTime: String? = null
    val AppVersion: AppVersion? = null
    //var Functions: List<Function>? = null
}