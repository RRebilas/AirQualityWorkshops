package pl.rrebilas.airquality.data

import pl.rrebilas.airquality.data.airly.AirlyMapper
import pl.rrebilas.airquality.data.airly.AirlyService
import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import javax.inject.Inject

class AirlyStationsDataSource @Inject constructor(private val airlyService: AirlyService) :
    RemoteStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        val installations = airlyService.getInstallations()
        return installations.map {
            AirlyMapper().mapInstallation(it)
        }
    }
}