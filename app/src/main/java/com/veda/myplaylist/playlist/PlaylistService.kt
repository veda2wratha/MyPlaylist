package com.veda.myplaylist.playlist

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.lang.RuntimeException
import javax.inject.Inject

class PlaylistService @Inject constructor(private val api: PlaylistAPI) {
    suspend fun fetchPlaylist() : Flow<Result<List<Playlist>>> {
        return flow {
            emit(Result.success(api.fetchAllPlaylists()))
        }.catch {
            Log.e("Error",it.toString())
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }
}
