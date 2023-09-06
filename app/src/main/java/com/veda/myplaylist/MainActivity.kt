package com.veda.myplaylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.veda.myplaylist.playlist.MyPlaylistRecyclerViewAdapter
import com.veda.myplaylist.playlist.Playlist
import com.veda.myplaylist.playlist.PlaylistViewModel
import com.veda.myplaylist.playlist.PlaylistViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PlaylistViewModel

    @Inject
    lateinit var viewModelFactory: PlaylistViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, viewModelFactory)[PlaylistViewModel::class.java]
        observeLoader()

        viewModel.playlists.observe(this as LifecycleOwner) { playlists ->
            if (playlists.getOrNull() != null)
                setupList(playlists.getOrNull()!!)
            else {
                Log.e("Error", "error")
            }
        }
    }

    private fun observeLoader() {
        val loader: ProgressBar = findViewById(R.id.load)
        viewModel.loader.observe(this as LifecycleOwner) { loading ->
            when (loading) {
                true -> loader.visibility = View.VISIBLE
                else -> loader.visibility = View.GONE
            }
        }
    }

    private fun setupList(
        playlists: List<Playlist>
    ) {
        val adapter = MyPlaylistRecyclerViewAdapter(playlists)
        val recyclerView: RecyclerView = findViewById(R.id.playlists_list)
        recyclerView.adapter = adapter
    }
}