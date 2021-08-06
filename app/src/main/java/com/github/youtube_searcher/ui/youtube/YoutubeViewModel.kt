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
        isBufferNotEmpty = buffer.isNotEmpty()
        Log.i("!@#", "Buffer ${buffer.toString()}")
    }
    fun removeFromBuffer(item: MappedYoutubeItem){
        buffer.remove(item)
        isBufferNotEmpty = buffer.isNotEmpty()
        Log.i("!@#", "Buffer ${buffer.toString()}")
    }

    fun addBufferToPlaylist(){
        Log.i("!@#", "Buffer ${buffer.toString()}")
        viewModelScope.launch (Dispatchers.IO){
            repositoryInterface.addToPlaylist(buffer)
        }
        buffer.clear()
        isBufferNotEmpty = false
        Log.i("!@#", "Buffer ${buffer.toString()}")
    }

    fun searchYoutube(search: String) {
        try{
            Log.i("!@#", "View model")
            viewModelScope.launch(Dispatchers.IO) {
                pagingFlow = repositoryInterface.searchYoutube(search)
            }
        }catch (e: Exception){
            Log.e("!@#", "Unexpected Error : ${e.message.toString()}")
        }
    }
}