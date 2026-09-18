package eu.vctrl4.business.core


import kotlinx.coroutines.flow.*

abstract class BaseLocalDataUseCase<Params, Result>() {

    abstract suspend fun run(key: Params): Result

    fun execute(params: Params): Flow<DataState<Result>> = flow {
        try {
            val result = run(params)

            emit(DataState.Data(result))
        } catch (e: Exception) {
            e.printStackTrace()
            //emit(handleUseCaseException(e))
        } finally {
            emit(DataState.Loading(ProgressBarState.Idle))
        }
    }

}
