package eu.vctrl4.business.datasource.storage.entities

import androidx.room.*
import kotlinx.serialization.*


@Serializable
@Entity(tableName = "user_profile", inheritSuperIndices = true)
class UserProfile()
{
    @PrimaryKey
    @ColumnInfo(index = true)
    var Email: String? = null

    //@ColumnInfo(index = true)
    //var AgreementAcceptedDate: String? = null // 2020-01-10

    //@ColumnInfo(index = true)
    //var CertificateNames: List<String?>? = null

    //@ColumnInfo(index = true)
    //var ClientOptions: List<String?>? = null

    @ColumnInfo(index = true)
    var Phone: String? = null

    @ColumnInfo(index = true)
    var UserName: String? = null

    //@ColumnInfo(index = true)
    //var Info: String? = null

    @ColumnInfo(index = true)
    var Functions: List<String?>? = null

    //@ColumnInfo(index = true)
    //var IsAgreementAccepted: Boolean? = null

    //@ColumnInfo(index = true)
    //var Roles: List<Role?>? = null

    //val Latitude: Double? = null
    //val Longitude: Double? = null
}
