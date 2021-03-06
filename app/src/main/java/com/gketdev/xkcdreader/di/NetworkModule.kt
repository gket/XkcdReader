package com.gketdev.xkcdreader.di

import com.gketdev.xkcdreader.data.service.ExplanationApi
import com.gketdev.xkcdreader.data.service.XkcdApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val baseUrl = "https://xkcd.com/"
    private const val secondaryUrl = "https://www.explainxkcd.com/wiki/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        return builder.build()
    }

    @Provides
    @Singleton
    @Named("XkcdService")
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideXkcdService(@Named("XkcdService") retrofit: Retrofit): XkcdApi {
        return retrofit.create(XkcdApi::class.java)
    }


    @Provides
    @Singleton
    @Named("Explanation")
    fun provideRetrofitForSecondaryApi(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(secondaryUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton

    fun provideExplanationService(@Named("Explanation") retrofit: Retrofit): ExplanationApi {
        return retrofit.create(ExplanationApi::class.java)
    }


}