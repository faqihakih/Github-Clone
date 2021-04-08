package com.akih.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akih.submission2bfaa.Utils.RetrofitGithub
import com.akih.submission2bfaa.data.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listFollowers = MutableLiveData<ArrayList<Users>>()

    fun setListFollowers(username : String){
        RetrofitGithub.apiInstance
                .getFollowerUsers(username)
                .enqueue(object : Callback<ArrayList<Users>>{
                    override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                        Log.d("onFailure", "$t.message")
                    }

                    override fun onResponse(call: Call<ArrayList<Users>>, response: Response<ArrayList<Users>>) {
                        if (response.isSuccessful){
                            listFollowers.postValue(response.body())
                        }
                    }

                })
    }

    fun getListFollowers(): LiveData<ArrayList<Users>>{
        return listFollowers
    }
}