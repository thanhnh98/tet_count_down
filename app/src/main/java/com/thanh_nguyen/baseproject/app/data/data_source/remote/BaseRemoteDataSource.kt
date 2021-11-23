package com.thanh_nguyen.baseproject.app.data.data_source.remote

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.thanh_nguyen.baseproject.app.model.respone.ErrorResponse
import com.thanh_nguyen.baseproject.app.model.respone.Result
import com.thanh_nguyen.baseproject.common.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import retrofit2.Response
import java.util.concurrent.CancellationException


/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseRemoteDataSource {
    protected fun <T> getResult(call: suspend () -> Response<T>): Flow<Result<T>> {
        return flow {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(Result.success(body))
                        return@flow
                    }
                }
                emit(error<T>(
                    " ${response.code()} ${response.message()}",
                    response.errorBody(),
                    errorCode = response.code()
                ))
            } catch (e: Exception) {
                e.printStackTrace()
                when (e) {
                    is CancellationException ->
                        emit(error<T>(
                            e.message ?: e.toString(),
                            null,
                            errorCode = Constants.Exception.CANCELLATION_EXCEPTION
                        ))
                    else -> emit(error<T>(e.message ?: e.toString(), null, errorCode = null))
                }
            }
        }
    }

    private fun <T> error(message: String, errorBody: ResponseBody?, errorCode: Int?): Result<T> {
        Log.e("BaseRemoteDataSource", "Network call has failed for a following reason: $message")
        return if (errorBody == null) {
            Result.error(
                "Network call has failed for a following reason: $message",
                errorCode = errorCode,
                error = null
            )
        } else {
            val gson = Gson()
            val type = object : TypeToken<ErrorResponse>() {}.type
            val errorData: ErrorResponse = gson.fromJson(errorBody.charStream(), type)
            Result.error(
                "Network call has failed for a following reason: $message",
                errorCode = errorCode,
                error = errorData
            )
        }
    }


}