package bobby.irawan.base.android.project.data.commons

import android.accounts.NetworkErrorException
import bobby.irawan.moviecatalogue.data.remote.common.BaseResponse
import bobby.irawan.moviecatalogue.data.remote.common.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException

/**
 * Created by Bobby Irawan on 15/12/20.
 */

suspend fun <RequestType> networkBaseResponseHandling(
    callApi: suspend () -> Response<BaseResponse<RequestType>>
) = flow<Resource<RequestType>> {
    emit(Resource.loading(null))
    try {
        val requestResult = callApi.invoke()
        with(requestResult) {
            when {
                isSuccessful -> {
                    val response = body() as BaseResponse<RequestType>

                    if (response.results != null) {
                        body()?.let {
                            emit(Resource.success(response.results))
                        }
                    } else {
                        emit(
                            Resource.error(
                                ErrorResponse(
                                    code(),
                                    "Data tidak ditemukan",
                                    ErrorType.OTHER
                                ), null
                            )
                        )
                    }
                }
                else -> {
                    val errorBody =
                        Gson().fromJson(errorBody()?.stringSuspending(), ErrorResponse::class.java)
                    emit(
                        Resource.error(
                            ErrorResponse(
                                code(),
                                errorBody.message,
                                null
                            ), null
                        )
                    )
                }
            }
        }
    } catch (e: Exception) {
        when (e) {
            is NetworkErrorException, is UnknownHostException -> {
                emit(
                    Resource.error(
                        ErrorResponse(
                            message = "Tidak ada koneksi internet",
                            type = ErrorType.NO_INTERNET
                        ), null
                    )
                )
            }
            else -> emit(
                Resource.error(
                    ErrorResponse(
                        message = "Maaf, terjadi kesalahan pada server",
                        type = ErrorType.OTHER
                    ),
                    null
                )
            )
        }
    }
}

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun ResponseBody.stringSuspending() =
    withContext(Dispatchers.IO) { string() }