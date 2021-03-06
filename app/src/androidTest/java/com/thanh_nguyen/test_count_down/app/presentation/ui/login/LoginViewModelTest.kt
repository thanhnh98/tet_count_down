/*
 * Created by Thanh Nguyen on 10/25/21, 10:55 AM
 */

package com.thanh_nguyen.test_count_down.app.presentation.ui.login

import androidx.annotation.UiThread
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.thanh_nguyen.test_count_down.app.data.repository.LoginRepositoryImplTest
import com.thanh_nguyen.test_count_down.common.base.mvvm.viewmodel.BaseViewModelTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock

@ExperimentalCoroutinesApi
@SmallTest
@RunWith(AndroidJUnit4::class)
class LoginViewModelTest: BaseViewModelTest() {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    var actorScope: TestCoroutineScope? = null

    @Mock
    lateinit var loginUseCase: LoginUseCase
    @Mock
    lateinit var loginRepositoryImplTest: LoginRepositoryImplTest

    @Before
    fun setUp() {
        loginRepositoryImplTest = LoginRepositoryImplTest()
        loginUseCase = LoginUseCase(
            loginRepositoryImplTest
        )
    }

    @After
    fun tearDown() {
    }

    @UiThread
    @Test
    fun testAuthorFromViewModel() = testScope.runBlockingTest{
        var isTrue: Boolean
        async {
             loginUseCase.getAuthorInfo().collect {
                 isTrue = true
                 job.cancel()
             }
            isTrue = false
            assert(isTrue)
        }
    }
}