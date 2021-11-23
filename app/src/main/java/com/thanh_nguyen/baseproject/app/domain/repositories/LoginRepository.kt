package com.thanh_nguyen.baseproject.app.domain.repositories

import com.thanh_nguyen.baseproject.app.model.AuthorModel
import com.thanh_nguyen.baseproject.app.model.respone.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun getAuthorInfo(): Flow<Result<AuthorModel>>
}