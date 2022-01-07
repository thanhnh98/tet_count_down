/*
 * Created by Thanh Nguyen on 11/24/21, 11:28 AM
 */

package com.thanh_nguyen.test_count_down.di

import com.thanh_nguyen.test_count_down.common.AdsManager
import com.thanh_nguyen.test_count_down.common.SoundManager
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

private const val OTHER = "OTHER"
val othersModule = Kodein.Module("OTHER", false) {
    bind() from singleton {
        SoundManager()
    }

    bind() from singleton {
        AdsManager(instance())
    }
}