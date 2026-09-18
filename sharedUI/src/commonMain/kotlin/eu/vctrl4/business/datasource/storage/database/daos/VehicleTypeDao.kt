package eu.vctrl4.storage.database.daos


import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface VehicleTypeDao
{
/*    @Query("DELETE FROM vehicle_type WHERE 1")
    fun delAllVehicleTypes()*/

    @Query("SELECT * FROM vehicle_type")
    suspend fun getAllVehicleTypesAsync(): List<VehicleType> // id: String

    @Query("SELECT * FROM vehicle_type")
    fun getAllVehicleTypesAsFlow(): Flow<List<VehicleType>> // id: String

    @Query("SELECT * FROM vehicle_type WHERE Id = :id") //
    suspend fun getVehicleTypeByIdAsync(id: String): List<VehicleType>

    @Query("DELETE FROM vehicle_type WHERE 1")
    suspend fun delAllVehicleTypesAsync()

    @Upsert
    suspend fun upsertAllVehicleTypes(vehicleTypes: List<VehicleType>)

    @Transaction
    suspend fun delAndUpsertAllVehicleTypes(vehicleTypes: List<VehicleType>) {
        delAllVehicleTypesAsync()
        upsertAllVehicleTypes(vehicleTypes)
    }

/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vehicleType: VehicleType)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vehicleType: VehicleType)

    @Delete
    fun delete(vehicleType: VehicleType)*/
}