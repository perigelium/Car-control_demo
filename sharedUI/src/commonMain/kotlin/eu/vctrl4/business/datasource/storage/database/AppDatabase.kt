package eu.vctrl4.business.datasource.storage.database


import androidx.room.*
import eu.vctrl4.business.constants.*
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.storage.database.daos.*

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> { // essential for iOS
	override fun initialize(): AppDatabase
}

@Database(entities = [Company::class,
	VehicleOption::class, VehicleType::class, VehicleCapacityClass::class
					 ], version = Constants.DB_VERSION, exportSchema = false)
@TypeConverters(eu.vctrl4.storage.database.TypeConverters::class)
@ConstructedBy(AppDatabaseConstructor::class) // essential for iOS
abstract class AppDatabase : RoomDatabase() {

	abstract fun companyDao(): CompanyDao?
	abstract fun vehicleCapacityClassDao(): VehicleCapacityClassDao?
	abstract fun vehicleOptionDao(): VehicleOptionDao?
	abstract fun vehicleTypeDao(): VehicleTypeDao?
	//abstract fun vehicleSubtypeDao(): VehicleSubtypeDao?
	//abstract fun vehicleModelDao(): VehicleModelDao?
	//abstract fun natureOfWorkDao(): NatureOfWorkDao?
}