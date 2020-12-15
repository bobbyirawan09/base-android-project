package bobby.irawan.moviecatalogue.data.remote.common

import com.google.gson.annotations.SerializedName

/**
 * Created by Bobby Irawan on 25/10/20.
 */
data class BaseResponse<T>(
    val results: T? = null,
    @SerializedName("total_pages")
    val total_pages: Int,
    @SerializedName("total_results")
    val total_results: Int
)