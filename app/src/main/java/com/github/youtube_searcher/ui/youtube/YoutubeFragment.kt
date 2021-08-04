package com.github.youtube_searcher.ui.youtube

import android.graphics.Color
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
import androidx.room.Room
import com.github.youtube_searcher.R
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.repository.room.PlaylistDatabase
import com.github.youtube_searcher.ui.adapters.YoutubePagingAdapter
import com.github.youtube_searcher.ui.adapters.YoutubeViewHolder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [YoutubeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class YoutubeFragment : Fragment() {

    private val viewModel: YoutubeViewModel by viewModels()
    private lateinit var pagingAdapter: YoutubePagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    //Добавляет или убирает обьект из буффера по чекбоксу
    private val onCheckListener: (item: MappedYoutubeItem, isChecked: Boolean) -> Unit =
        { item: MappedYoutubeItem, isChecked: Boolean ->
            if (isChecked) viewModel.addToBuffer(item)
            else viewModel.removeFromBuffer(item)
        }

    override fun onResume() {
        super.onResume()
        val recyclerView = activity?.findViewById<RecyclerView>(R.id.youtube_recycler_view)
        pagingAdapter = YoutubePagingAdapter(onCheckListener)
        recyclerView?.adapter = pagingAdapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.searchYoutube()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.pagingFlow?.collectLatest {
                Log.i("!@#", "Data collected $it")
                pagingAdapter.submitData(it)
            }
        }
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest {
                when (it.refresh) {
                    is LoadState.NotLoading -> Log.i("!@#", "Not loading")
                    LoadState.Loading -> Log.i("!@#", "Loading")
                    is LoadState.Error -> Log.i("!@#", "Error loading")
                    else -> {
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
        return inflater.inflate(R.layout.fragment_youtube, container, false)

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YoutubeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}