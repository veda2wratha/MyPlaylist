package com.veda.myplaylist.playlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlaylistViewModel(
   private val repository: PlaylistRepository
) : ViewModel() {
    val playlists = liveData<Result<List<Playlist>>> {
        emitSource(
            repository.getPlaylists().asLiveData()
        )
    }
}
