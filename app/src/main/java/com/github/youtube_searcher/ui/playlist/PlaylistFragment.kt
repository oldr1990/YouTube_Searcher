package com.github.youtube_searcher.ui.playlist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.youtube_searcher.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PlaylistFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PlaylistFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var pagingAdapter: PlaylistPagingAdapter
    private val viewModel: PlaylistViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPlaylist()

    }

    override fun onResume() {
        super.onResume()
        pagingAdapter = PlaylistPagingAdapter(viewModel.deleteItem)
        val recyclerView =
            activity?.findViewById<RecyclerView>(R.id.playlist_recycler_view)
        recyclerView?.adapter = pagingAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.pagingFlow?.collectLatest {
                Log.i("!@#", "Playlist: Fragment Data collected $it")
                pagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.NotLoading -> Log.i("!@#", "Playlist: Not loading")
                    LoadState.Loading -> Log.i("!@#", "Playlist: Loading")
                    is LoadState.Error -> Log.i("!@#", "Playlist: " + it.refresh.toString())
                    else -> {
                        Log.i("!@#", "Playlist: Unexpected state")
                    }
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}