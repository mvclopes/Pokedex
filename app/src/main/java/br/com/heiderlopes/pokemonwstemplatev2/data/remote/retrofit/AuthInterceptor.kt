package br.com.heiderlopes.pokemonwstemplatev2.data.remote.retrofit

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

private val TAG = AuthInterceptor::class.simpleName
private const val UNAUTHORIZED_ERROR = 401

object AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Authorization", "Basic cG9rZWFwaTpwb2tlbW9u")
            val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code() == UNAUTHORIZED_ERROR) {
            Log.e(TAG, "Error API KEY")
        }
        return response
    }

}