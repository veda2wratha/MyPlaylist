package com.veda.myplaylist.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class PlaylistViewModelFactory(
   private val repository: PlaylistRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return PlaylistViewModel(repository) as T
    }

}
