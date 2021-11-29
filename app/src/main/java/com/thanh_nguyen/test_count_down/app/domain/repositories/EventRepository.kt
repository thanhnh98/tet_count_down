/*
 * Created by Thanh Nguyen on 11/24/21, 9:26 AM
 */

package com.thanh_nguyen.test_count_down.app.domain.repositories

import com.thanh_nguyen.test_count_down.app.model.WishModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getWishes(): Flow<Result<WishModel>>
}