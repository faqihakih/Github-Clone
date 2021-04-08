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

class FollowingViewModel : ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<Users>>()

    fun setListFollowing(username : String){
        RetrofitGithub.apiInstance
                .getFollowingUsers(username)
                .enqueue(object : Callback<ArrayList<Users>>{
                    override fun onFailure(call: Call<ArrayList<Users>>, t: Throwable) {
                        Log.d("onFailure", "$t.message")
                    }

                    override fun onResponse(call: Call<ArrayList<Users>>, response: Response<ArrayList<Users>>) {
                        if (response.isSuccessful){
                            listFollowing.postValue(response.body())
                        }
                    }

                })
    }

    fun getListFollowing(): LiveData<ArrayList<Users>>{
        return listFollowing
    }
}