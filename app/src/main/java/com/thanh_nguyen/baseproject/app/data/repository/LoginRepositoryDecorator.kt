/*
 * Created by Thanh Nguyen on 11/2/21, 11:10 AM
 */

package com.thanh_nguyen.baseproject.app.data.repository

import com.thanh_nguyen.baseproject.app.domain.repositories.LoginRepository
import com.thanh_nguyen.baseproject.app.model.AuthorModel
import com.thanh_nguyen.baseproject.app.model.respone.Result
import kotlinx.coroutines.flow.Flow

class LoginRepositoryDecorator(
    private val target: LoginRepository,
): LoginRepository by target{
    override fun getAuthorInfo(): Flow<Result<AuthorModel>> {
        return target.getAuthorInfo()
    }
}