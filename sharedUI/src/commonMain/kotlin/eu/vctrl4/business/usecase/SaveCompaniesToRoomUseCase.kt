package eu.vctrl4.business.usecase

import eu.vctrl4.business.core.*
import eu.vctrl4.business.datasource.storage.database.daos.CompanyDao
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

class SaveCompaniesToRoomUseCase (
    private val companyDao: CompanyDao
) : BaseRoomManagedUseCase<List<Company>, List<Company>, Unit>() {

    override val progressBarType = ProgressBarState.Idle
    override val showLoading = false

    override fun queryFlow(params: List<Company>): Flow<List<Company>?> = emptyFlow()

    override suspend fun querySingle(params: List<Company>): List<Company> {
        companyDao.delAndUpsertAllCompanies(params)
        return params
    }

    override fun convertEntity(entity: List<Company>?) {
        return Unit
    }
}