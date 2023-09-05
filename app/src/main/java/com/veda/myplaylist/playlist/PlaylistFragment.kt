package com.veda.myplaylist.playlist

import android.os.Bundle
import android.util.Log
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
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaylistFragment : Fragment() {

    private var columnCount = 1
    private lateinit var viewModel: PlaylistViewModel
    private lateinit var viewModelFactory: PlaylistViewModelFactory
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://openwhyd.org/")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(PlaylistAPI::class.java)
    private var service = PlaylistService(api)
    private val repository = PlaylistRepository(service)

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
                        println(it);
                        adapter = MyPlaylistRecyclerViewAdapter(it.getOrNull()!!)
                    }
                }
            } else {
                Log.d("Erro", it.isFailure.toString())
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