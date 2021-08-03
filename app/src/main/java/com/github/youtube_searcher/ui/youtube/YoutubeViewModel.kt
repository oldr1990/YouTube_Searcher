package com.github.youtube_searcher.ui.youtube

import android.graphics.Movie
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.github.youtube_searcher.Constants.YoutubeSettings.TEST_SEARCH
import com.github.youtube_searcher.model.MappedYoutubeItem
import com.github.youtube_searcher.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class YoutubeViewModel @Inject constructor(
    private val repositoryInterface: RepositoryInterface
    ) :
    ViewModel() {

    var pagingFlow: Flow<PagingData<MappedYoutubeItem>>? = null

    fun searchYoutube(search: String = TEST_SEARCH) {
        try{
            Log.i("!@#", "View model")
            viewModelScope.launch(Dispatchers.IO) {
                pagingFlow = repositoryInterface.searchYoutube(search)
            }
        }catch (e: Exception){
            print(e.message + "!@#")
        }
    }
}