

import eu.vctrl4.business.datasource.storage.database.daos.CompanyDao
import eu.vctrl4.business.datasource.storage.entities.*
import eu.vctrl4.business.usecase.*
import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersFilterViewModelTest {

	private val testDispatcher = UnconfinedTestDispatcher()

	// Mock implementation of the local database access layer (Room DAO)
	private val mockCompanyDao = object : CompanyDao {
		var stubbedDatabaseCompanies: List<Company> = emptyList()

		override fun getAllCompaniesAsFlow(): Flow<List<Company>> {
			return flowOf(stubbedDatabaseCompanies)
		}

		override suspend fun delAllCompaniesAsync() {
			TODO("Not yet implemented")
		}

		override suspend fun upsertAllCompanies(companies: List<Company>) {
			TODO("Not yet implemented")
		}

		override suspend fun upsertCompany(company: Company) {
			TODO("Not yet implemented")
		}

		override suspend fun getAllCompaniesAsync(): List<Company> {
			return stubbedDatabaseCompanies
		}
	}

	// Concrete mock instance of the domain UseCase layer inheriting production logic
	private val realUseCase = object : GetCompaniesFromRoomUseCase(mockCompanyDao) {
		override suspend fun querySingle(params: Unit): List<Company> {
			return mockCompanyDao.getAllCompaniesAsync()
		}
	}

	private lateinit var viewModel: OrdersFilterViewModel

	@BeforeTest
	fun setUp() {
		// Intercept Dispatchers.Main boundaries using unconfined test runner
		Dispatchers.setMain(testDispatcher)
		viewModel = OrdersFilterViewModel(realUseCase)
	}

	@Test
	fun `on FilterInvoked should filter expired departments and append zero-index defaults`() = runBlocking {

		val supplierDepartmentValid = Department().apply {
			HasChildren = false
			ExpirationDate = null
		}
		val supplierDepartmentExpired = Department().apply {
			HasChildren = false
			ExpirationDate = "2026-09-21"
		}

		val supplierCompany = Company().apply {
			IsSupplier = true
			Departments = listOf(supplierDepartmentValid, supplierDepartmentExpired)
		}

		val customerCompany = Company().apply {
			IsCustomer = true
			Departments = mutableListOf()
		}

		mockCompanyDao.stubbedDatabaseCompanies = listOf(supplierCompany, customerCompany)

		// Fire the state machine sequence inside the ViewModel via the MVI interface
		viewModel.onTriggerEvent(OrdersFilterViewEvent.OnFilterInvoked)

		// Allow Dispatchers.IO background thread to finish mapping data
		var attempts = 0
		while (viewModel.state.value.suppDepartments.isEmpty() && attempts < 40) {
			delay(50)
			attempts++
		}

		val finalState = viewModel.state.value

		// Expired items dropped, default "All" departments item injected
		assertEquals(2, finalState.suppDepartments.size)

		// Active valid supplier fields are updated accordingly
		assertEquals(finalState.suppDepartments.first().IsSupplier, true)

		// All companies placeholder appended successfully at index zero
		assertEquals(2, finalState.custCompanies.size)
	}

	@AfterTest
	fun tearDown() {
		// Reset global coroutine boundaries back to default
		Dispatchers.resetMain()
	}
}






