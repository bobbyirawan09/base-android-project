package bobby.irawan.base.android.project.data.commons

import bobby.irawan.moviecatalogue.data.remote.common.BaseResponse
import retrofit2.Response

/**
 * Created by Bobby Irawan on 28/10/20.
 */

typealias SimpleResult<T> = Result<T>

typealias SimpleBaseResponse<T> = Response<BaseResponse<T>>

typealias SimpleResponse<T> = Response<T>