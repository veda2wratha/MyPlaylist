package com.veda.myplaylist.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.veda.myplaylist.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PlaylistsRepositoryShould : BaseUnitTest() {
    private var service: PlaylistService = mock()
    private var playlist = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun fetchPlaylistFromService() =
        runTest {
            val repository = PlaylistRepository(service)
            repository.getPlaylists()
            verify(service, times(1)).fetchPlaylist()
        }

    @Test
    fun emitPlaylistFromService() =
        runTest {
            val repository = mockSuccessFulTest()
            assertEquals(playlist, repository.getPlaylists().first().getOrNull())

        }

    private suspend fun mockSuccessFulTest(): PlaylistRepository {
        whenever(service.fetchPlaylist()).thenReturn(
            flow {
                emit(Result.success(playlist))
            }
        )
        return PlaylistRepository(service)
    }

    @Test
    fun propagateErrors() = runTest {
        val repository = mockFailureCase()

        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylist()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )
        return PlaylistRepository(service)
    }
}