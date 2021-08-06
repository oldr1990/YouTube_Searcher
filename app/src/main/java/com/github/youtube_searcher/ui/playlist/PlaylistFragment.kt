package com.github.youtube_searcher.ui.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.youtube_searcher.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var pagingAdapter: PlaylistPagingAdapter
    private val viewModel: PlaylistViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //load playlist from db
        viewModel.getPlaylist()

        //init
        pagingAdapter = PlaylistPagingAdapter(viewModel.deleteItem)
        val progressBar = activity?.findViewById<ConstraintLayout>(R.id.playlist_waitingConstraint)
        val recyclerView =
            activity?.findViewById<RecyclerView>(R.id.playlist_recycler_view)
        recyclerView?.adapter = pagingAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        //collect playlist
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingFlow?.collectLatest {
                Log.i("!@#", "Playlist: Fragment Data collected $it")
                pagingAdapter.submitData(it)
            }
        }

        // прогресс бар
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.NotLoading -> progressBar?.visibility = View.GONE
                    LoadState.Loading -> progressBar?.visibility = View.VISIBLE
                    is LoadState.Error -> {
                        progressBar?.visibility = View.GONE
                        Log.i("!@#", it.refresh.toString())
                    }
                    else -> {
                        progressBar?.visibility = View.GONE
                        Log.i("!@#", "Unexpected state")
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false)
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            PlaylistFragment().apply {
            }
    }
}