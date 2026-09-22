package eu.vctrl4.business.datasource.storage.database.daos

import androidx.room.*
import eu.vctrl4.business.datasource.storage.entities.*
import kotlinx.coroutines.flow.*

@Dao
interface CompanyDao
{
    @Query("SELECT * FROM company") //  WHERE id = :id
    suspend fun getAllCompaniesAsync(): List<Company> // id: String

    @Query("SELECT * FROM company") //  WHERE id = :id
    fun getAllCompaniesAsFlow(): Flow<List<Company>> // id: String

    @Query("DELETE FROM company WHERE 1")
    suspend fun delAllCompaniesAsync()

    @Upsert
    suspend fun upsertAllCompanies(companies: List<Company>)

    @Transaction
    suspend fun delAndUpsertAllCompanies(companies: List<Company>) {
        delAllCompaniesAsync()
        upsertAllCompanies(companies)
    }

    @Upsert
    suspend fun upsertCompany(company: Company)

/*    @Query("DELETE FROM company WHERE 1")
    fun delAllCompanies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(company: Company)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(company: Company)

    @Delete
    fun delete(company: Company)*/
}