package petros.efthymiou.groovy.playlist
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.veda.myplaylist.playlist.Playlist
import com.veda.myplaylist.playlist.PlaylistAPI
import com.veda.myplaylist.playlist.PlaylistService
import com.veda.myplaylist.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.lang.RuntimeException

class PlaylistServiceShould : BaseUnitTest() {

    private lateinit var service: PlaylistService
    private val api: PlaylistAPI = mock()
    private val playlists: List<Playlist> = mock()

    @Test
    fun fetchPlaylistsFromAPI() = runTest {
        service = PlaylistService(api)
        service.fetchPlaylist().first()
        verify(api, times(1)).fetchAllPlaylists()
    }

    @Test
    fun convertValuesToFlowResultAndEmitsThem() = runTest {
        mockSuccessfulCase()
        assertEquals(Result.success(playlists), service.fetchPlaylist().first())
    }

    @Test
    fun emitsErrorResultWhenNetworkFails() = runTest {
        mockErrorCase()
        assertEquals("Something went wrong",
            service.fetchPlaylist().first().exceptionOrNull()?.message)
    }

    private suspend fun mockErrorCase() {
        whenever(api.fetchAllPlaylists()).thenThrow(RuntimeException("Damn backend developers"))

        service = PlaylistService(api)
    }

    private suspend fun mockSuccessfulCase() {
        whenever(api.fetchAllPlaylists()).thenReturn(playlists)
        service = PlaylistService(api)
    }

}