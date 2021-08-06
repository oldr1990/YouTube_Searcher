package com.github.youtube_searcher.ui.playlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel
@Inject constructor(
    private val repository: RepositoryInterface
) : ViewModel() {
    var pagingFlow: Flow<PagingData<MappedYoutubeItem>>? = null

    val deleteItem: (itemToDelete: MappedYoutubeItem) -> Unit =
        { item ->
            Log.e("!@#", "Deleting: $item")
            viewModelScope.launch(Dispatchers.IO) { repository.deleteFromPlaylist(item) }
            getPlaylist()
        }

    fun getPlaylist() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                pagingFlow = repository.getPlaylist()
            }
        } catch (e: Exception) {
            Log.e("!@#", "Unexpected Error : ${e.message.toString()}")
        }
    }
}