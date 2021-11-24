/*
 * Created by Thanh Nguyen on 10/25/21, 2:47 PM
 */

package com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel

import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope

open class BaseViewModelTest{
    val job = Job()
    val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(job + testDispatcher)
}