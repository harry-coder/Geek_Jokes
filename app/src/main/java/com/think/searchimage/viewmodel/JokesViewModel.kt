package com.think.searchimage.viewmodel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.think.searchimage.database.DBHelper
import com.think.searchimage.model.Joke
import com.think.searchimage.remote.Event
import com.think.searchimage.remote.NetworkResponse
import com.think.searchimage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(private val repository:Repository, private val dbHelper: DBHelper): BaseViewModel() {

   private val jokesMediatorLiveData=MediatorLiveData<Event<List<Joke>>>()
   val _jokesLiveData=jokesMediatorLiveData

    init {
        makeRequestTimer()
    //getJokesFromServer()
    }

    private fun makeRequestTimer(){
        /** 1 minute delay */
        startCoroutineTimer(60000,0,){
            getJokesFromServer()
        }
    }
    private fun getJokesFromServer()  {
        viewModelScope.launch {
            when (val data = repository.getJokesFromServer()) {
                is NetworkResponse.Success -> {
                    ioThenMain( work = suspend {dbHelper.jokeDao().insert(data.body, Date())} ){
                        getJokesFromDb()
                    }

                }
                else -> handleError(data)
            }
        }
    }

    private fun getJokesFromDb(){
        viewModelScope.launch {
          val liveData=  withContext(Dispatchers.IO){
              dbHelper.jokeDao().getJokesList()
            }
            jokesMediatorLiveData.addSource(liveData) {
                _jokesLiveData.value=Event(it)
            }
        }


    }

}