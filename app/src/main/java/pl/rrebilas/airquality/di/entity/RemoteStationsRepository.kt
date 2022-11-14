package pl.rrebilas.airquality.di.entity

interface RemoteStationsRepository {
    suspend fun getAll(): List<AQStation>
}