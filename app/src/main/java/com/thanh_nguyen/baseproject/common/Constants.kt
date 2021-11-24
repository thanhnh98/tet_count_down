package com.thanh_nguyen.baseproject.common

import com.thanh_nguyen.baseproject.R

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