package pl.rrebilas.airquality.di.logic

import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import javax.inject.Inject

class GetStationsUseCase @Inject constructor(
    private val remoteStationsRepository: RemoteStationsRepository
) {

    suspend fun execute(): List<AQStation> {
        return remoteStationsRepository.getAll()
    }
}