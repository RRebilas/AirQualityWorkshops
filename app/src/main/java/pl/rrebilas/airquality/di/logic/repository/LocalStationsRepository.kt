package pl.rrebilas.airquality.di.logic.repository

import pl.rrebilas.airquality.di.entity.AQStation

interface LocalStationsRepository {
    suspend fun getAll(): List<AQStation>
    suspend fun save(stations: List<AQStation>)
}