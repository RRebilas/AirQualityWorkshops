package pl.rrebilas.airquality.data.local

import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.logic.repository.LocalStationsRepository

class InMemoryStationsRepository : LocalStationsRepository {
    override suspend fun getAll(): List<AQStation> {
        TODO("Not yet implemented")
    }

    override suspend fun save(stations: List<AQStation>) {
        TODO("Not yet implemented")
    }
}