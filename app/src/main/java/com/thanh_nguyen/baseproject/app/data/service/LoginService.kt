package com.thanh_nguyen.baseproject.app.data.service

import com.thanh_nguyen.baseproject.app.model.AuthorModel
import retrofit2.Response
import retrofit2.http.GET

interface LoginService {
    @GET("thanhnh98/adc65e53a20cd4392aa6d6712ae4ad30/raw/016ba588f0ed5656aa5cf62fe5d0757f037ebfc7/gistfile1.txt")
    suspend fun getAuthorInfo(): Response<AuthorModel>
}