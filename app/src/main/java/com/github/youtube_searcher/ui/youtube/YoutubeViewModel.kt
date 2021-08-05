package com.github.youtube_searcher.ui.youtube


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
class YoutubeViewModel @Inject constructor(
    private val repositoryInterface: RepositoryInterface
    ) :
    ViewModel() {

    var inputValue = ""
    var isBufferNotEmpty = false
    var pagingFlow: Flow<PagingData<MappedYoutubeItem>>? = null

    private val buffer = mutableListOf<MappedYoutubeItem>()

    //ограничение доступа к буфферу
    fun addToBuffer(item: MappedYoutubeItem){
        buffer.add(item)
        isBufferNotEmpty = true
    }
    fun removeFromBuffer(item: MappedYoutubeItem){
        buffer.remove(item)
        isBufferNotEmpty = buffer.isNotEmpty()
    }

    fun addBufferToPlaylist(){
        repositoryInterface.addToPlaylist(buffer)
        buffer.removeAll(buffer)
    }

    fun searchYoutube(search: String) {
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