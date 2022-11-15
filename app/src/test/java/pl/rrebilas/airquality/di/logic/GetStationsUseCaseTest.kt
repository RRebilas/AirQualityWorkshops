package pl.rrebilas.airquality.di.logic

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import pl.rrebilas.airquality.di.entity.AQStation
import pl.rrebilas.airquality.di.entity.RemoteStationsRepository
import pl.rrebilas.airquality.di.logic.repository.LocalStationsRepository


class GetStationsUseCaseTest {
    lateinit var sut: GetStationsUseCase
    private lateinit var remote: MockRemoteStationsRepository
    private lateinit var local: MockLocalStationsRepository

    @Before
    fun setup() {
        remote = MockRemoteStationsRepository()
        local = MockLocalStationsRepository()
        sut = GetStationsUseCase(remoteStationsRepository = remote, localStationsRepository = local)
    }

    @Test
    fun init_DoesNotMakeAnyRemoteCalls() {
        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeMakesOneCallToLocal() = runBlocking {
        sut.execute()
        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeMakesCallToRemoteWhenLocalDataIsEmpty() = runBlocking {
        local.getAllResults = emptyList()

        sut.execute()

        assertEquals(true, remote.getAllCalled)
    }

    @Test
    fun executeDoesNotMakeCallToRemoteWhenLocalDataNotEmpty() = runBlocking {
        local.getAllResults = listOf(sampleAQStation)

        sut.execute()

        assertEquals(false, remote.getAllCalled)
    }

    @Test
    fun executeDoesMakeOneCallToLocal() = runBlocking {
        sut.execute()

        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeDoesMakeOneCallToLocalForNonEmptyData() = runBlocking {
        local.getAllResults = listOf(sampleAQStation)

        sut.execute()

        assertEquals(1, local.getAllCallsCount)
    }

    @Test
    fun executeReturnLocalStationsWhenRemoteStationRepositoryIsCalled() = runBlocking {
        local.getAllResults = emptyList()
        remote.getAllResults = listOf(sampleAQStation)

        val actual = sut.execute()

        assertEquals("1", actual.first().id)
    }

    @Test
    fun executeSavesStationsToLocalWhenRemoteIsNonEmpty() = runBlocking {
        local.getAllResults = emptyList()
        remote.getAllResults = listOf(sampleAQStation)

        sut.execute()

        assertEquals(true, local.saveCalled)
        assertEquals("1", local.saveReceivedArguments.first().id)
    }

    @Test
    fun executesReturnValidLocalListStations() = runBlocking {
        val sampleAQStation2 = AQStation("2", "name", "city", "sponsor", "")
        local.getAllResults = listOf(sampleAQStation, sampleAQStation2)

        val actual = sut.execute()

        assertEquals("1", actual.first().id)
        assertEquals("2", actual[1].id)

    }

    private var sampleAQStation = AQStation("1", "name", "city", "sponsor", "")
}

class MockLocalStationsRepository : LocalStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    var getAllResults: List<AQStation> = emptyList()

    val saveCalled: Boolean
        get() = saveAllCallsCount > 0
    var saveAllCallsCount: Int = 0
    var saveReceivedArguments: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return getAllResults
    }

    override suspend fun save(stations: List<AQStation>) {
        saveReceivedArguments = stations
        saveAllCallsCount++
    }

}

class MockRemoteStationsRepository : RemoteStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    var getAllResults: List<AQStation> = emptyList()

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return getAllResults
    }
}
