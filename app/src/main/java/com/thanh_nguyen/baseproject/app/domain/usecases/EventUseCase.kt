/*
 * Created by Thanh Nguyen on 11/24/21, 9:46 AM
 */

package com.thanh_nguyen.baseproject.app.domain.usecases

import com.thanh_nguyen.baseproject.app.domain.repositories.EventRepository

class EventUseCase(
    private val repository: EventRepository
) {
    fun getWishes() = repository.getWishes()
}