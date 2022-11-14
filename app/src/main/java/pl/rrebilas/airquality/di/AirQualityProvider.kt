package pl.rrebilas.airquality.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import pl.rrebilas.airquality.data.AirlyStationsDataSource
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AirQualityProvider {
    @Provides
    @Singleton
    fun provideRemoteStationsRepository(airlyService: AirlyStationsDataSource.AirlyService): RemoteStationsRepository {
        return AirlyStationsDataSource(airlyService)
    }

    @Provides
    @Singleton
    fun provideAirlyAuthOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(AirlyAuthInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(AirlyStationsDataSource.HOST)
            .build()
    }

    @Provides
    @Singleton
    fun provideAirlyService(retrofit: Retrofit): AirlyStationsDataSource.AirlyService {
        return retrofit.create(AirlyStationsDataSource.AirlyService::class.java)
    }
}

class AirlyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("apikey", "8OgIYZzbQUxKDcXLbZ0VHF6ZR9vQqkfg")
        return chain.proceed(requestBuilder.build())
    }
}