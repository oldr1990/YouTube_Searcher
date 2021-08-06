package com.github.youtube_searcher.ui.youtube

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.youtube_searcher.Constants.ERROR_INVALID_INPUT
import com.github.youtube_searcher.Constants.ERROR_UNEXPECTED
import com.github.youtube_searcher.R
import com.github.youtube_searcher.databinding.FragmentYoutubeBinding
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YoutubeFragment : Fragment() {
    private lateinit var binding: FragmentYoutubeBinding
    private val viewModel: YoutubeViewModel by viewModels()
    private lateinit var pagingAdapter: YoutubePagingAdapter
    private var floatingActionButton: FloatingActionButton? = null

    //Добавляет или убирает обьект из буффера по чекбоксу
    private val onCheckListener: (item: MappedYoutubeItem, isChecked: Boolean) -> Unit =
        { item: MappedYoutubeItem, isChecked: Boolean ->
            // Log.i("!@#", "CheckListener $isChecked")
            if (isChecked) viewModel.addToBuffer(item)
            else {
                viewModel.removeFromBuffer(item)
                floatingButtonVisibility(viewModel.isBufferNotEmpty)
            }
            floatingButtonVisibility(viewModel.isBufferNotEmpty)
        }

    //visibility of the floating button
    private fun floatingButtonVisibility(state: Boolean) {
        if (state) floatingActionButton?.show()
        else floatingActionButton?.hide()
        if (state) floatingActionButton?.setOnClickListener {
            viewModel.addBufferToPlaylist()
            floatingButtonVisibility(viewModel.isBufferNotEmpty)
        }
        //  Log.i("!@#", "Floating Button Visibility $state")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //init
        floatingActionButton = activity?.findViewById<FloatingActionButton>(R.id.floating_button)
        val progressBar = activity?.findViewById<ConstraintLayout>(R.id.waitingConstraint)
        val inputTextView = activity?.findViewById<TextView>(R.id.input_text)
        pagingAdapter = YoutubePagingAdapter(onCheckListener)
        val recyclerView =
            activity?.findViewById<RecyclerView>(R.id.youtube_recycler_view)

        //setup floating button
        floatingButtonVisibility(viewModel.isBufferNotEmpty)

        //saving input data
        inputTextView?.text = viewModel.inputValue
        inputTextView?.addTextChangedListener {
            viewModel.inputValue = it.toString().trim()
        }

        //search button
        activity?.findViewById<Button>(R.id.search_button)?.setOnClickListener {
            val inputText = inputTextView?.text

            //проверка на корректный ввод
            if (inputText.toString().trim() != "" && inputText != null) {
                viewModel.searchYoutube(inputText.toString().trim())
                recyclerView?.adapter = pagingAdapter
                recyclerView?.layoutManager = LinearLayoutManager(requireContext())
                inputTextView.text = viewModel.inputValue

                //приём пегинг данных
                lifecycleScope.launch {
                    viewModel.pagingFlow?.collectLatest {
                        Log.i("!@#", "Data collected $it")
                        pagingAdapter.submitData(it)
                    }
                }

                //слушаем стейты для дебага
                lifecycleScope.launch {
                    pagingAdapter.addLoadStateListener {
                        when (it.append) {
                            is LoadState.NotLoading -> Log.i("!@#", "Load state NotLoading")
                            LoadState.Loading -> Log.i("!@#", "Loadstate loading")
                            is LoadState.Error -> Log.i("!@#", "Error " + it.refresh.toString())
                            else -> Log.i("!@#", "Unexpected state")
                        }
                    }

                    //прогресс бар
                    pagingAdapter.loadStateFlow.collectLatest {
                        when (it.refresh) {
                            is LoadState.NotLoading -> progressBar?.visibility = View.GONE
                            LoadState.Loading -> progressBar?.visibility = View.VISIBLE
                            is LoadState.Error -> {
                                progressBar?.visibility = View.GONE
                                Toast.makeText(requireContext(),  it.refresh.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                            else -> {
                                progressBar?.visibility = View.GONE
                                Toast.makeText(requireContext(),  ERROR_UNEXPECTED, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            } else Toast.makeText(requireContext(), ERROR_INVALID_INPUT, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYoutubeBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_youtube, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            YoutubeFragment().apply {
            }
    }
}