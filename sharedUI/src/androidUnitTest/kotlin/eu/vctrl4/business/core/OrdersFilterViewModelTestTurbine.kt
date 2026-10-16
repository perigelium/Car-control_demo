package eu.vctrl4.business.core

import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.OrdersFilterViewModel
import app.cash.turbine.test
import eu.vctrl4.business.datasource.storage.database.daos.CompanyDao
import eu.vctrl4.business.datasource.storage.entities.Company
import eu.vctrl4.business.usecase.GetCompaniesFromRoomUseCase
import kotlinx.coroutines.flow.flowOf
import kotlin.test.assertEquals
import eu.vctrl4.business.datasource.storage.entities.Department
import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.OrdersFilterViewEvent
import eu.vctrl4.presentation.ui.orders.order_list.filter.view_model.OrdersFilterViewState
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrdersFilterViewModelTestTurbine {

	private val testDispatcher = UnconfinedTestDispatcher()

	private val mockCompanyDao = object : CompanyDao {
		var stubbedDatabaseCompanies: List<Company> = emptyList()
		override fun getAllCompaniesAsFlow(): Flow<List<Company>> =
			flowOf(stubbedDatabaseCompanies)
		override suspend fun delAllCompaniesAsync() = TODO()
		override suspend fun upsertAllCompanies(companies: List<Company>) = TODO()
		override suspend fun upsertCompany(company: Company) = TODO()
		override suspend fun getAllCompaniesAsync(): List<Company> =
			stubbedDatabaseCompanies
	}

	private val realUseCase = object : GetCompaniesFromRoomUseCase(mockCompanyDao) {
		override suspend fun querySingle(params: Unit): List<Company> =
			mockCompanyDao.getAllCompaniesAsync()
	}

	private lateinit var viewModel: OrdersFilterViewModel

	@Before
	fun setUp() {
		Dispatchers.setMain(testDispatcher)
		viewModel = OrdersFilterViewModel(realUseCase)
	}

	@After
	fun tearDown() = Dispatchers.resetMain()

	@Test
	fun `on FilterInvoked should filter expired departments and append zero-index defaults`() =
		runTest(testDispatcher) {

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

			mockCompanyDao.stubbedDatabaseCompanies =
				listOf(supplierCompany, customerCompany)

/* 			@Test
			fun `on FilterInvoked should filter expired departments and append zero-index defaults`() =
				runTest(testDispatcher) { */

					// ... build supplierDepartmentValid / supplierCompany / mock data ...
					//mockCompanyDao.stubbedDatabaseCompanies = listOf(supplierCompany, customerCompany)

/* 					viewModel.setEvent(OrdersFilterViewEvent.OnFilterInvoked)

					advanceUntilIdle()                     // ← replaces the whole `delay(50)` loop

					val finalState = viewModel.stateFlow.value

					assertEquals(2, finalState.suppDepartments.size)
					assertTrue(finalState.suppDepartments.first().IsSupplier == true)
					assertEquals(2, finalState.custCompanies.size) */

			viewModel.stateFlow.test {
				awaitItem()                               // initial

				viewModel.setEvent(OrdersFilterViewEvent.OnFilterInvoked)

				var state = awaitItem()
				while (state.suppDepartments.isEmpty()) { // skip Loading / Idle
					state = awaitItem()
				}

				assertEquals(2, state.suppDepartments.size)
				assertTrue(state.suppDepartments.first().IsSupplier == true)
				assertEquals(2, state.custCompanies.size)

				cancelAndIgnoreRemainingEvents()
			}
				}

/* 			viewModel.stateFlow.test {
				awaitItem()
				viewModel.setEvent(OrdersFilterViewEvent.OnFilterInvoked)
				val finalState = awaitItem()
				assertEquals(2, finalState.suppDepartments.size)
				assertTrue(finalState.suppDepartments.first().IsSupplier == true)
				assertEquals(2, finalState.custCompanies.size)
				cancelAndIgnoreRemainingEvents()
			} */

}