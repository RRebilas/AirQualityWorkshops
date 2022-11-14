package pl.rrebilas.airquality.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import pl.rrebilas.airquality.di.logic.FakeRemoteStationsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AirQualityProvider {
    @Provides
    @Singleton
    fun provideRemoteStationsRepository(): RemoteStationsRepository {
        return FakeRemoteStationsRepository()
    }
}