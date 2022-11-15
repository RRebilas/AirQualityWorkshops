package pl.rrebilas.airquality.di.logic

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository


class GetStationsUseCaseTest {
    @Test
    fun init_DoesNotMakeAnyRemoteCalls() {
        val remote = MockRemoteStationsRepository()
        GetStationsUseCase(remoteStationsRepository = remote)
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOnlyOneCallToRemote() = runBlocking {
        val remote = MockRemoteStationsRepository()
        val sut = GetStationsUseCase(remoteStationsRepository = remote)
        sut.execute()
        assertEquals(1, remote.getAllCallsCount)
    }
}

class MockRemoteStationsRepository : RemoteStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return listOf()
    }


}
