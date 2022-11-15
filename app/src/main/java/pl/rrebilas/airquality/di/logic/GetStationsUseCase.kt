package pl.rrebilas.airquality.di.logic

import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import pl.rrebilas.airquality.di.logic.repository.LocalStationsRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val remoteStationsRepository: RemoteStationsRepository,
    private val localStationsRepository: LocalStationsRepository
) {

    suspend fun execute(): List<AQStation> {
        val localStations = localStationsRepository.getAll()
        if (localStations.isEmpty()) {
            val remoteStations = remoteStationsRepository.getAll()
            return emptyList()
        }
        return localStations
    }
}