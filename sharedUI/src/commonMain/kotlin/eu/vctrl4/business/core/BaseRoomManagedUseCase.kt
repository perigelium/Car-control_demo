package eu.vctrl4.business.core


import androidx.sqlite.*
import autocontrol.sharedui.generated.resources.*
import eu.vctrl4.*
import eu.vctrl4.common.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.io.*

abstract class BaseRoomManagedUseCase<Params, Entity, Result>()
{
    abstract fun queryFlow(params: Params): Flow<Entity?>

    abstract suspend fun querySingle(params: Params): Entity?

    abstract fun convertEntity(entity: Entity?): Result?

    abstract val progressBarType: ProgressBarState

    open val showLoading: Boolean = true

    fun execute(params: Params, isFlow: Boolean = true): Flow<DataStateLocal<Result>> = flow {
        try
        {
            if (showLoading) emit(DataStateLocal.Loading(progressBarType))

            if (isFlow)
            {
                queryFlow(params).distinctUntilChanged()
                    .flowOn(Dispatchers.IO).collect { entity ->
                        emit(DataStateLocal.Data(data = convertEntity(entity)))
                    }
            } else
            {
                val entity = withContext(Dispatchers.IO) { querySingle(params) }
                emit(DataStateLocal.Data(data = convertEntity(entity)))
            }
        } catch (e: Exception)
        {
            emit(handleException(e))
        } finally
        {
            if (showLoading) emit(DataStateLocal.Loading(ProgressBarState.Idle))
        }
    }

    private fun handleException(e: Exception): DataStateLocal<Result>
    {
        e.printStackTrace()

        val message = when (e)
        {
            is SQLiteException -> "Local database error: ${e.message}"
            is IOException -> "IO error: ${e.message}"
            else -> e.message ?: Res.string.unknown_error.asState
        }

        return if (BuildKonfig.DEBUG)
        {
            DataStateLocal.Display(UIComponent.DialogTitleText(e::class.simpleName?:"", message))
        } else
        {
            DataStateLocal.Display(UIComponent.Toast(Res.string.error_load_local_database.asState))
        }
    }
}
