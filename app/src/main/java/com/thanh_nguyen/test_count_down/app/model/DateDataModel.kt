/*
 * Created by Thanh Nguyen on 11/23/21, 12:59 PM
 */

package com.thanh_nguyen.test_count_down.app.model

sealed class DateDataModel(
    val day: Long,
    val hour: Long,
    val minute: Long,
    val second: Long
){
    data class LunarNewYear(
        val dayLunar: Long,
        val hourLunar: Long,
        val minuteLunar: Long,
        val secondLunar: Long
    ): DateDataModel(
        dayLunar,
        hourLunar,
        minuteLunar,
        secondLunar
    )
}