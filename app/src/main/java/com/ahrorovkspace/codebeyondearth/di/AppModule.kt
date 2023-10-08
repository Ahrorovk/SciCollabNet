package com.ahrorovkspace.codebeyondearth.di

import android.content.Context
import com.ahrorovkspace.codebeyondearth.core.Constants
import com.ahrorovkspace.codebeyondearth.data.local.DataStoreManager
import com.ahrorovkspace.codebeyondearth.data.network.CBERepositoryImpl
import com.ahrorovkspace.codebeyondearth.data.network.remote.CBEApi
import com.ahrorovkspace.codebeyondearth.domain.CBERepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideCBEApi(): CBEApi =
        Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .build()
            )
            .build()
            .create(CBEApi::class.java)

    @Singleton
    @Provides
    fun provideCBERepository(
        api: CBEApi
    ): CBERepository = CBERepositoryImpl(api)

    @Singleton
    @Provides
    fun provideSessionManager(
        @ApplicationContext context: Context
    ) = DataStoreManager(context)
}