package com.thanh_nguyen.baseproject.app.data.network

import okhttp3.Interceptor

class AuthInterceptor : Interceptor {
    companion object{
        private val headers: HashMap<String, String> = HashMap()

        fun addHeader(header: Pair<String, String>){
            headers[header.first] = header.second
        }

        fun setToken(token: String){
            headers["x-access-token"] = token
        }
    }

    init {
        setToken("thanhnh98")
    }

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request().newBuilder()
        headers.forEach {
            request.addHeader(it.key, it.value)
        }
        return chain.proceed(request.build())
    }
}