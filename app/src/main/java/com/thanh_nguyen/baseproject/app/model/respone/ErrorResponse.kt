package com.thanh_nguyen.baseproject.app.model.respone

import com.thanh_nguyen.baseproject.app.model.BaseModel

data class ErrorResponse (
    var result: String?,
    var result_code: Int?
): BaseModel()
