package com.veda.myplaylist.playlist

import retrofit2.http.GET

interface PlaylistAPI {
    @GET("adrien?format=json")
    suspend fun fetchAllPlaylists(): List<Playlist>
}
