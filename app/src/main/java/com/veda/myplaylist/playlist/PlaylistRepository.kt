package com.veda.myplaylist.playlist

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlaylistRepository(
    private val service: PlaylistService
) {
    suspend fun getPlaylists(): Flow<Result<List<Playlist>>> {
        return service.fetchPlaylist()
    }
}
