package com.thanh_nguyen.baseproject.app.model.respone


/**
 * A generic class that holds a value with its loading status.
 *
 * Result is usually created by the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */
data class Result<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val error: ErrorResponse? = null,
    val errorCode: Int? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(
            message: String,
            data: T? = null,
            errorCode: Int? = null,
            error: ErrorResponse? = null
        ): Result<T> {
            return Result(Status.ERROR, data, message, error = error, errorCode = errorCode)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(Status.LOADING, data, null)
        }

    }

}

//fun <T : Any?, O> Result<T>.mapTo(mapper: IMapper<T, O>): Result<O> {
//    return if (this.status == Result.Status.LOADING) Result.loading(null)
//    else if (this.status == Result.Status.ERROR || this.data == null) {
//        Result.error(
//            this.message ?: "",
//            errorCode = this.errorCode,
//            error = null
//        )
//    } else {
//        Result.success(mapper.map(this.data))
//    }
//}

inline fun <reified T, R : Result<T>> R.onResultReceived(
    onLoading: () -> Unit,
    onSuccess: (result: R) -> Unit,
    onError: (result: R) -> Unit
) {
    when (status) {
        Result.Status.LOADING -> onLoading.invoke()
        Result.Status.SUCCESS -> onSuccess.invoke(this)
        Result.Status.ERROR -> onError.invoke(this)
    }
}