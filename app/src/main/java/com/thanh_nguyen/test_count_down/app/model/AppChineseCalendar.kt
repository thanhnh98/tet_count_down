package com.thanh_nguyen.test_count_down.app.model

import android.icu.util.ChineseCalendar
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class AppChineseCalendar: ChineseCalendar() {
    fun getMagic() = fieldResolutionTable
}