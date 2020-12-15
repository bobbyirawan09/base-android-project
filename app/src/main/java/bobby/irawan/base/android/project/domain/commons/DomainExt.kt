package bobby.irawan.base.android.project.domain.commons

import bobby.irawan.base.android.project.data.commons.ErrorType
import bobby.irawan.base.android.project.data.commons.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

suspend fun <Data, MappedData> Flow<Resource<Data>>.mapToResult(
    map: (data: Data?) -> Result.Success<MappedData>
): Flow<Result<MappedData>> = flow {
    this@mapToResult.collect { resourceData ->
        when (resourceData.status) {
            Resource.Status.SUCCESS -> {
                emit(map(resourceData.data))
            }
            Resource.Status.ERROR -> {
                when (resourceData.error?.type) {
                    ErrorType.NO_INTERNET -> emit(Result.State.NoInternet)
                    else -> emit(Result.Failure(resourceData.error))
                }
            }
            Resource.Status.LOADING -> {
                emit(Result.State.Loading)
            }
        }
    }
}
