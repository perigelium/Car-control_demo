package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.database.daos.CompanyDao
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

open class GetCompaniesFromRoomUseCase(
    private val companyDao: CompanyDao
                                      ) : BaseRoomManagedUseCase<Unit, List<Company>, List<Company>>() {

    override val progressBarType = ProgressBarState.Loading

    override fun queryFlow(params: Unit): Flow<List<Company>?>
    {
        return companyDao.getAllCompaniesAsFlow()
    }

    override suspend fun querySingle(params: Unit): List<Company> {
        return companyDao.getAllCompaniesAsync()
    }

    override fun convertEntity(entity: List<Company>?): List<Company> {
        return entity ?: emptyList()
    }
}