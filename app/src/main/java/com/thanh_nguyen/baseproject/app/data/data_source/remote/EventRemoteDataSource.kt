/*
 * Created by Thanh Nguyen on 11/24/21, 9:34 AM
 */

package com.thanh_nguyen.baseproject.app.data.data_source.remote

import com.thanh_nguyen.baseproject.app.data.service.EventService

class EventRemoteDataSource(private val eventService: EventService): BaseRemoteDataSource() {
    fun getWishes() = getResult {
        eventService.getWishes()
    }
}