/*
 * Created by Thanh Nguyen on 11/24/21, 9:37 AM
 */

package com.thanh_nguyen.test_count_down.app.data.repository.event

import com.thanh_nguyen.test_count_down.app.data.data_source.remote.EventRemoteDataSource
import com.thanh_nguyen.test_count_down.app.domain.repositories.EventRepository
import com.thanh_nguyen.test_count_down.app.model.WishModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(private val dataSource: EventRemoteDataSource): EventRepository {
    override fun getWishes(): Flow<Result<WishModel>> {
        return dataSource.getWishes()
    }
}