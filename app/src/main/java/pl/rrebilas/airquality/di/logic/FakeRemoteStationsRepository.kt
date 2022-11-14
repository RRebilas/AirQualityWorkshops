package pl.rrebilas.airquality.di.logic

import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import javax.inject.Singleton

@Singleton
class FakeRemoteStationsRepository : RemoteStationsRepository{
    override suspend fun getAll(): List<AQStation> {
        return emptyList()
    }
}