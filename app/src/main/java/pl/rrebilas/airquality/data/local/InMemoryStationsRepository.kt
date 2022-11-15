package pl.rrebilas.airquality.data.local

import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.logic.repository.LocalStationsRepository

class InMemoryStationsRepository : LocalStationsRepository {

    companion object {
        private var stations: List<AQStation> = emptyList()
    }

    override suspend fun getAll(): List<AQStation> {
        return stations
    }

    override suspend fun save(stations: List<AQStation>) {
        InMemoryStationsRepository.stations = stations
    }
}