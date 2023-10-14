package ru.astondevs.worldnews.data.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.astondevs.worldnews.BuildConfig

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val newURL = original.url.newBuilder()
            .addQueryParameter("apiKey", BuildConfig.API_KEY)
            .build()

        return chain.proceed(
            original.newBuilder()
                .url(newURL)
                .build()
        )
    }
}
