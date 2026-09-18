package eu.vctrl4.storage.database.daos


import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface VehicleCapacityClassDao
{
/*    @Query("DELETE FROM vehicle_capacity_class WHERE 1")
    fun delAllVehicleCapacityClasses()*/

    @Query("SELECT * FROM vehicle_capacity_class")
    suspend fun getAllVehicleCapacityClassesAsync(): List<VehicleCapacityClass> // id: String

    @Query("SELECT * FROM vehicle_capacity_class")
    fun getAllVehicleCapacityClassesAsFlow(): Flow<List<VehicleCapacityClass>> // id: String

    @Query("SELECT * FROM vehicle_capacity_class WHERE Id = :id") //
    suspend fun getVehicleCapacityClassByIdAsync(id: String): List<VehicleCapacityClass>

    @Query("DELETE FROM vehicle_capacity_class WHERE 1")
    suspend fun delAllVehicleCapacityClassesAsync()

    @Upsert
    suspend fun upsertAllVehicleCapacityClasses(vehicleCapacityClasss: List<VehicleCapacityClass>)

    @Transaction
    suspend fun delAndUpsertAllVehicleCapacityClasses(vehicleCapacityClasss: List<VehicleCapacityClass>) {
        delAllVehicleCapacityClassesAsync()
        upsertAllVehicleCapacityClasses(vehicleCapacityClasss)
    }

/*    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vehicleCapacityClass: VehicleCapacityClass)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vehicleCapacityClass: VehicleCapacityClass)

    @Delete
    fun delete(vehicleCapacityClass: VehicleCapacityClass)*/
}