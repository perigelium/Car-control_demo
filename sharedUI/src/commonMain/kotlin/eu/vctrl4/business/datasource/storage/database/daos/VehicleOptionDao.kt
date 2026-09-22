package eu.vctrl4.business.datasource.storage.database.daos


import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface VehicleOptionDao
{
    @Query("SELECT * FROM vehicle_option")
    suspend fun getAllVehicleOptionsAsync(): List<VehicleOption> // id: String

    @Query("SELECT * FROM vehicle_option")
    fun getAllVehicleOptionsAsFlow(): Flow<List<VehicleOption>> // id: String

    @Query("SELECT * FROM vehicle_option WHERE Id = :id") //
    suspend fun getVehicleOptionByIdAsync(id: String): List<VehicleOption>

    @Query("DELETE FROM vehicle_option WHERE 1")
    suspend fun delAllVehicleOptionsAsync()

    @Upsert
    suspend fun upsertAllVehicleOptions(vehicleOptions: List<VehicleOption>)

    @Transaction
    suspend fun delAndUpsertAllVehicleOptions(vehicleOptions: List<VehicleOption>) {
        delAllVehicleOptionsAsync()
        upsertAllVehicleOptions(vehicleOptions)
    }

/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vehicleOption: VehicleOption)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vehicleOption: VehicleOption)

    @Delete
    fun delete(vehicleOption: VehicleOption)*/
}