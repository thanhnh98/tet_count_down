package com.thanh_nguyen.test_count_down.app.model.respone

import com.thanh_nguyen.test_count_down.app.model.BaseModel


data class BaseResponse<T>(
    val success: String?,
    val result_code: Int?,
    val result: String?,
    val data: T?
): BaseModel()