package com.app.travelbuddy.di

import android.content.Context
import android.content.SharedPreferences
import com.app.travelbuddy.BuildConfig
import com.app.travelbuddy.data.local.AppDatabase
import com.app.travelbuddy.data.local.dao.CountryDao
import com.app.travelbuddy.data.remote.CountryRemoteDataSource
import com.app.travelbuddy.data.remote.service.CityService
import com.app.travelbuddy.data.remote.service.IpApiService
import com.app.travelbuddy.data.repository.CountryRepository
import com.app.travelbuddy.utils.ConstantUtil
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, mapper: ObjectMapper): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(okHttpClient)
            .build()


    @Provides
    fun provideJackson(): ObjectMapper = ObjectMapper()
        .registerModule(KotlinModule())
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .callTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(
            ConstantUtil.PREFERENCE_TAG,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideCityService(retrofit: Retrofit): CityService =
        retrofit.create(CityService::class.java)

    @Provides
    fun provideIpApiService(retrofit: Retrofit): IpApiService =
        retrofit.create(IpApiService::class.java)

    @Singleton
    @Provides
    fun provideCountryRemoteDataSource(cityService: CityService, ipApiService: IpApiService) =
        CountryRemoteDataSource(cityService, ipApiService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.countryDao()

    @Singleton
    @Provides
    fun provideCountryRepository(
        remoteDataSource: CountryRemoteDataSource,
        localDataSource: CountryDao,
        sharedPreference: SharedPreferences
    ) = CountryRepository(remoteDataSource, localDataSource, sharedPreference)
}