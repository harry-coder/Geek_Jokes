package com.think.searchimage.viewmodel

import com.google.android.gms.maps.model.LatLng
import com.think.searchimage.database.DBHelper
import com.think.searchimage.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val repository: Repository,
    private val dbHelper: DBHelper
) : BaseViewModel() {

     var centerLatLng: LatLng = LatLng(28.606075, 77.361916) // default lat lgn


   /* fun saveLocationData(userLocation: LocationEntity) = liveData<Event<Long?>> {
        withContext(Dispatchers.IO) {
            emit(Event(dbHelper.locationDao().insert(userLocation)))
        }
    }*/




}