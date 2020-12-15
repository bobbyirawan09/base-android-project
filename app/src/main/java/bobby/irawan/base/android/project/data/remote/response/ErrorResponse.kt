package bobby.irawan.moviecatalogue.data.remote.common

import bobby.irawan.base.android.project.data.commons.ErrorType
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("status_code")
    val code: Int? = 400,
    @SerializedName("status_message")
    val message: String? = "",
    val type: ErrorType?
)