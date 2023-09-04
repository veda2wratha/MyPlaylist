package com.veda.myplaylist.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.veda.myplaylist.R

class PlaylistFragment : Fragment() {

    private var columnCount = 1
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory
    private lateinit var repository: PlaylistRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)

        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner) {
            // Set the adapter
            if (it.getOrNull() != null) {
                if (view is RecyclerView) {
                    with(view) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = MyPlaylistRecyclerViewAdapter(it.getOrNull()!!)
                    }
                }
            }else{

            }
        }


        return view
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository);
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {}
            }
    }