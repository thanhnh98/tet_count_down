/*
 * Created by Thanh Nguyen on 11/24/21, 9:17 AM
 */

package com.thanh_nguyen.baseproject.app.data.service

import com.thanh_nguyen.baseproject.app.model.WishModel
import retrofit2.Response
import retrofit2.http.GET

interface EventService {
    @GET("main/wishes")
    suspend fun getWishes(): Response<WishModel>
}