/*
 * Created by Thanh Nguyen on 10/20/21, 4:42 PM
 */

package com.thanh_nguyen.baseproject.app.data.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.thanh_nguyen.baseproject.app.data.data_source.remote.LoginRemoteDataSource
import com.thanh_nguyen.baseproject.app.data.local_data.room_db.StorageDatabase
import com.thanh_nguyen.baseproject.app.data.network.ApiClient
import com.thanh_nguyen.baseproject.app.domain.repositories.LoginRepository
import com.thanh_nguyen.baseproject.app.domain.usecases.LoginUseCase
import com.thanh_nguyen.baseproject.app.model.AuthorModel
import com.thanh_nguyen.baseproject.app.model.entities.StorageItemEntity
import com.thanh_nguyen.baseproject.app.model.respone.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import kotlin.random.Random

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class LoginRepositoryImplTest: LoginRepository{
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var db: StorageDatabase

    @Mock
    val loginRemoteDataSource: LoginRemoteDataSource = LoginRemoteDataSource(
        ApiClient.createService()
    )

    @Before
    fun before(){
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StorageDatabase::class.java
        ).allowMainThreadQueries().build()


    }

    @After
    fun tearDown(){
        db.close()
    }

    @Test
    fun testGetAllItems() = runBlockingTest {
        getAllItems().collect {
            Log.e("THANH","get all items: ${Gson().toJson(it)}")
        }
        assert(true)
    }

    @Test
    fun testAddItems() = runBlockingTest {
        val itemAdd = StorageItemEntity(
            2,
            "THANH NE ${System.currentTimeMillis()}",
            Random(100).nextInt().toString()
        )
        async {
            insertItem(itemAdd)
            testGetAllItems()
        }
        assert(true)
    }

    @Test
    fun testDeleteItems(){
        GlobalScope.launch {
            db.storageItemDao().deleteItem(1)
            testGetAllItems()
        }
    }

    @Test
    fun testGetAuthor() = runBlockingTest{
        async {
            getAuthorInfo().collect {
                Log.e("data receied:", "${it.data}")
            }
        }
    }

    override fun getAuthorInfo(): Flow<Result<AuthorModel>> {
        Log.e("calling", "??")
        return loginRemoteDataSource.getAuthorInfo()
    }

    override fun getAllItems(): Flow<List<StorageItemEntity>> {
        return db.storageItemDao().getAllItems()
    }

    override suspend fun insertItem(item: StorageItemEntity) {
        return db.storageItemDao().insertItem(item)
    }
}