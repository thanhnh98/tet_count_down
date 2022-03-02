package com.thanh_nguyen.test_count_down.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext


suspend fun CoroutineScope.doOnIO(job: Job = Job(), invoker: () -> Unit){
    withContext(Dispatchers.IO + job){
        invoker.invoke()
    }
}

suspend fun CoroutineScope.doOnMain(job: Job = Job(), invoker: () -> Unit){
    withContext(Dispatchers.Main + job){
        invoker.invoke()
    }
}