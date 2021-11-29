package com.thanh_nguyen.test_count_down.app.model.response

import com.thanh_nguyen.test_count_down.app.model.BaseModel

data class ErrorResponse (
    var result: String?,
    var result_code: Int?
): BaseModel()
