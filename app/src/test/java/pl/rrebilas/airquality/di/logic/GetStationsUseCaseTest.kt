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

    private var sampleAQStation = AQStation("id", "name", "city", "sponsor", "")
}

class MockLocalStationsRepository : LocalStationsRepository {
    val getAllCalled: Boolean
        get() = getAllCallsCount > 0
    var getAllCallsCount: Int = 0
    var getAllResults: List<AQStation> = emptyList()

    val saveCalled: Boolean
        get() = saveAllCallsCount > 0
    var saveAllCallsCount: Int = 0

    override suspend fun getAll(): List<AQStation> {
        getAllCallsCount++
        return getAllResults
    }

    override suspend fun save(stations: List<AQStation>) {
        saveAllCallsCount++
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
