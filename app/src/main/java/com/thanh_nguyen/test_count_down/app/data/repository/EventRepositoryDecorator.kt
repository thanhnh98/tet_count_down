/*
 * Created by Thanh Nguyen on 11/24/21, 9:42 AM
 */

package com.thanh_nguyen.test_count_down.app.data.repository

import com.thanh_nguyen.test_count_down.app.domain.repositories.EventRepository
import com.thanh_nguyen.test_count_down.app.model.WishModel
import com.thanh_nguyen.test_count_down.app.model.response.Result
import kotlinx.coroutines.flow.Flow

class EventRepositoryDecorator(private val repository: EventRepository): EventRepository by repository {
    override fun getWishes(): Flow<Result<WishModel>> {
        return repository.getWishes()
    }
}