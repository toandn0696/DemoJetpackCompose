package com.example.composepokemondexproject.data.error

import java.net.HttpURLConnection

/**
 * Create by Nguyen Thanh Toan on 11/3/21
 *
 */
sealed class ErrorModel : Throwable() {
    open fun isCommonError(): Boolean = false

    companion object {
        const val API_ERROR_RESULT_CODE = "1"
    }

    sealed class Http : ErrorModel() {
        data class ApiError(
            val code: String?,
            override val message: String?,
            val apiUrl: String?
        ) : Http() {
            override fun isCommonError(): Boolean {
                if (code == HttpURLConnection.HTTP_UNAUTHORIZED.toString()
                    || code == HttpURLConnection.HTTP_INTERNAL_ERROR.toString()
                    || code == ApiErrorDetailCode.SERVER_MAINTENANCE_9001.code
                    || code == ApiErrorDetailCode.FORCE_UPDATE_8001.code
                    || code == ApiErrorDetailCode.NO_RESPONSE_FROM_SERVER_9002.code
                ) {
                    return true
                }
                return false
            }
        }
    }

    data class LocalError(val errorMessage: String, val code: String?) : ErrorModel()

    enum class LocalErrorException(val message: String, val code: String) {
        NO_INTERNET_EXCEPTION("Không kết nối được. Vui lòng thử lại sau", "1001"),
        REQUEST_TIME_OUT_EXCEPTION("Không kết nối được. Vui lòng thử lại sau", "1002"),
        UN_KNOW_EXCEPTION("Lỗi không xác định!", "1000")
    }

    enum class ApiErrorDetailCode(val code: String) {
        SERVER_MAINTENANCE_9001("9001"),
        FORCE_UPDATE_8001("8001"),
        NO_RESPONSE_FROM_SERVER_9002("9002")
    }
}