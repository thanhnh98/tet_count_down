package com.thanh_nguyen.test_count_down.common


class Constants {
    companion object{
        const val APP_ID = "3809D924-093D-466B-9E7D-BF7823718FB9"
        const val DEFAULT_MUSIC_NAME = "Hopeful Freedom"
        const val DEFAULT_MUSIC_SINGER_NAME= "Asher Fulero"
        const val NAME_OF_YEAR = "Quý Mão"
        const val NEW_YEAR = "2023"
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
        const val LUNAR_NEW_YEAR = "22/01/2023"
    }
}