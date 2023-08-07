package com.example.myapplication.androidCookies


import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GymsViewModel(
    private val stateHandle: SavedStateHandle
) :ViewModel(){
    var state by mutableStateOf(emptyList<Gym>())

    private var apiService:GymsApiService
    private lateinit var gymsCall:Call<List<Gym>>

    init {
        val retrofit:Retrofit = Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            ).baseUrl(
                "https://gyms-locations-default-rtdb.firebaseio.com/"
            ).build()

        apiService = retrofit.create(GymsApiService::class.java)

        getGyms()
    }

    // way 2 to solve network error main thread
    private fun getGyms(){
        gymsCall =  apiService.getGyms()
        gymsCall.enqueue(object :Callback<List<Gym>>{
            override fun onResponse(call: Call<List<Gym>>, response: Response<List<Gym>>) {
                response.body()?.let {
                    state = it.getSelectedGyms()
                }
            }
            override fun onFailure(call: Call<List<Gym>>, t: Throwable) {
                t.printStackTrace()
            }
        })
//        apiService.getGyms().execute().body()?.let {
//             state = it.getSelectedGyms()
//        }
    }

    override fun onCleared() {
        super.onCleared()
        gymsCall.cancel()
    }



    // way 1 to solve network error main thread
//    fun performNetworkOperation() {
//        // Run network operation in a background thread using CoroutineScope
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                // Perform your network operation here (e.g., making an HTTP request)
//                // Example: val response = makeNetworkRequest()
//                getGyms()
//            } catch (e: Exception) {
//                // Handle exceptions or errors here
//            }
//        }
//    }

    fun toggleFavoriteState(gymId:Int){
        val gyms = state.toMutableList()
        val itemIndex = gyms.indexOfFirst { it.id == gymId }
        gyms[itemIndex] = gyms[itemIndex].copy(isFavorite = !gyms[itemIndex].isFavorite)
        storeSelectedGymsFavorite(gyms[itemIndex])
        state = gyms
    }
//    fun togglePlaceeState(gym:Gym,context:Context){
//        val uri = Uri.parse("geo:0,0?q=${gym.place}")
//        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
//        context.startActivity(mapIntent)
//    }

    private fun storeSelectedGymsFavorite(gym: Gym){
        val savedHandleList = stateHandle.get<List<Int>?>(FAV_IDS).orEmpty().toMutableList()
        if (gym.isFavorite)savedHandleList.add(gym.id) else savedHandleList.remove(gym.id)
        stateHandle[FAV_IDS] = savedHandleList
    }

    private fun List<Gym>.getSelectedGyms():List<Gym>{
//        val gyms = getGyms()
        stateHandle.get<List<Int>?>(FAV_IDS)?.let { savedIDs ->
            savedIDs.forEach{selectedGymID ->
                this.find { it.id == selectedGymID }?.isFavorite = true
            }
        }
        return this
    }



    companion object{
        const val FAV_IDS = "favoriteGymsIDs"
    }

}