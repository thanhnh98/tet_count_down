package com.thanh_nguyen.test_count_down.common

import com.thanh_nguyen.test_count_down.R

class Constants {
    companion object{
        const val APP_ID = "3809D924-093D-466B-9E7D-BF7823718FB9"

        val GLOBAL_SOUND = listOf(
            R.raw.hpny,
        ) .random()
    }


    class Exception{
        companion object{
            val CANCELLATION_EXCEPTION = -999
        }
    }


    class Phrases{
        companion object{
            val listWishes = listOf(
                "Vạn Sự Như Ý" to "Phúc Lộc An Khang",
                "Phát Tài Phát Lộc" to "Mã Đáo Thành Công",
                "Vạn Sự Như Ý" to "Gia Chủ Phát Tài",
                "Năm Tăng Phú Quý" to "Ngày Hưởng Vinh Hoa",
                "Năm Xuân Như Ý" to "Tuổi Ngày Bình An",
            )
        }
    }

    class BundleKey{
        companion object{
            const val EMAIL = "EMAIL"
            const val NAME = "NAME"
        }
    }

    object EventDate{
        const val LUNAR_NEW_YEAR = "01/02/2022"
    }
}