package com.thanh_nguyen.baseproject.app.domain.usecases

import com.thanh_nguyen.baseproject.app.domain.repositories.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
    ) {
    fun getAuthorInfo() = loginRepository.getAuthorInfo()
}