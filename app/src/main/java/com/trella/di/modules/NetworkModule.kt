/*
 * *
 *  * Created by Ahmed Elshaer on 11/10/19 3:37 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/10/19 3:12 PM
 *
 */

package com.trella.di.modules

import com.trella.common.AuthInterceptor
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    internal fun provideRetrofitInterface(client: OkHttpClient): Retrofit {

        val mochi = MoshiConverterFactory.create()
        mochi.asLenient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(mochi)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }

    @Singleton
    @Provides
    internal fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(AuthInterceptor())
            .build()
    }

    companion object {
        const val BASE_URL = "https://case-api.trella.app/"
    }
}
