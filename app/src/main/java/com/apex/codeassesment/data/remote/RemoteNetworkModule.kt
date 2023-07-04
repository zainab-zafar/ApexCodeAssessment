package com.apex.codeassesment.data.remote

import com.apex.codeassesment.data.remote.UnsafeOkHttpClient.unsafeOkHttpClient
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RemoteNetworkModule {
    const val BASE_URL = "https://randomuser.me/"

    @Provides
    fun provideOkHttpClient() =
        unsafeOkHttpClient

    @Provides
    fun provideGSONConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create(
            GsonBuilder()
                .setLenient()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
        )

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): RemoteEndPoints = retrofit.create(RemoteEndPoints::class.java)

    @Provides
    fun provideMoshiInstance() = Moshi.Builder().build()
}
