package eu.vctrl4.di


import androidx.room.*
import androidx.sqlite.driver.bundled.*
import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.network.main.*
import eu.vctrl4.business.datasource.storage.database.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.common.*
import eu.vctrl4.presentation.ui.appstart.login.view_model.*
import eu.vctrl4.presentation.ui.logout.*
import eu.vctrl4.presentation.ui.onlineboard.map.view_model.VehicleTrackViewModel
import eu.vctrl4.presentation.ui.onlineboard.order_list.filter.view_model.*
import eu.vctrl4.presentation.ui.onlineboard.order_list.view_model.*
import eu.vctrl4.presentation.ui.orders.order_details.view_model.*
import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.*
import eu.vctrl4.presentation.ui.orders.order_list.order_history.OrderHistoryViewModel
import eu.vctrl4.presentation.ui.orders.order_list.view_model.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.filter.view_model.*
import eu.vctrl4.presentation.ui.tickets.ticket_list.view_model.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import org.koin.core.context.*
import org.koin.core.module.dsl.*
import org.koin.dsl.*

fun createDatabase(builder: RoomDatabase.Builder<AppDatabase>): AppDatabase {
	return builder.setDriver(BundledSQLiteDriver())
		.setQueryCoroutineContext(Dispatchers.IO).build()
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
	appDeclaration()
	modules(
		koinAppModule(), koinDatabaseModule(), platformModule
	       )
}

fun koinAppModule() = module {

	singleOf(::ConfigUseCaseKtor)

	single<MainService> { MainServiceImpl(get()) }

	single { Json { isLenient = true; ignoreUnknownKeys = true } }

	single {
		ktorHttpClient()
	}

	viewModel { BoardOrderListViewModel(get(), get()) }
	single { BoardOrderListUseCase(get(), get()) }
	single { BoardOrdersFilterViewModel(get()) }

	single { GetVehiclePositionUseCase(get()) }
	single { GetVehicleTrackUseCase(get()) }
	viewModel { VehicleTrackViewModel(get(), get()) }

	single { TicketsFilterViewModel(get()) }
	viewModel { TicketListViewModel(get()) }

	viewModel { OrderListViewModel(get(), get()) }
	single { OrderListUseCase(get(), get()) }
	single { OrdersCountUseCase(get(), get()) }
	single { OrdersFilterViewModel(get()) }

	single { OrderHistoryUseCase(get(), get()) }
	viewModel { OrderHistoryViewModel(get()) }

	single { OrderDetailsUseCase(get()) }
	single { CancelOrderUseCase(get()) }

	single { RequestVehicleTypesUseCase(get()) }
	single { RequestVehicleOptionsUseCase(get()) }
	single { RequestVehicleCapacityClassesUseCase(get()) }

	single { GetVehicleTypesFromRoomUseCase(get()) }
	single { GetVehicleOptionsFromRoomUseCase(get()) }
	single { GetVehicleCapacityClassesFromRoomUseCase(get()) }

	single { SaveVehicleTypesToRoomUseCase(get()) }
	single { SaveVehicleOptionsToRoomUseCase(get()) }
	single { SaveVehicleCapacityClassesToRoomUseCase(get()) }
	viewModel {
		OrderDetailsViewModel(
			get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
		                     )
	}

	single { RequestCompaniesUseCase(get(), get()) }
	single { AuthUseCaseKtor(get(), get()) }

	single { SaveCompaniesToRoomUseCase(get()) }
	single { GetCompaniesFromRoomUseCase(get()) }

	viewModel {
		try {
			LoginViewModel(
				configUseCaseKtor = get(),
				authUseCaseKtor = get(),
				requestCompaniesUseCase = get(),
				saveCompaniesToRoomUseCase = get(),
				getCompaniesFromRoomUseCase = get(),
				prefsStoreManager = get()
			              )
		} catch (e: Exception) {
			// This bypasses the nested crash framework and prints the actual cause string on iOS
			println("=== KOIN CRASH SUB-ROOT CAUSE: ${e.message} ===")
			e.printStackTrace()
			throw e
		}
	}
	single { DeleteFirebaseTokenUseCase(get(), get()) }
	single { DeleteFirebaseTokenUseCase(get(), get()) }
	viewModel { LogoutViewModel(get(), get()) }
}

fun koinDatabaseModule() = module {

	single { get<AppDatabase>().companyDao() }
	single { get<AppDatabase>().vehicleCapacityClassDao() }
	single { get<AppDatabase>().vehicleOptionDao() }
	single { get<AppDatabase>().vehicleTypeDao() }
	//single { get<AppDatabase>().vehicleSubtypeDao() }
}