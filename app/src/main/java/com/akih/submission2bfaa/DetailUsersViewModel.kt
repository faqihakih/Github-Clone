package com.akih.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akih.submission2bfaa.Utils.RetrofitGithub
import com.akih.submission2bfaa.data.DetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUsersViewModel : ViewModel() {
    val users = MutableLiveData<DetailUser>()

    fun setDetailUser(username : String){
        RetrofitGithub.apiInstance
            .getDetailUsers(username)
            .enqueue(object : Callback<DetailUser>{
                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("onFailure", "$t.message")
                }

                override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                    if (response.isSuccessful){
                        users.postValue(response.body())
                    }
                }

            })
    }

    fun getDetailUser() : LiveData<DetailUser>{
        return users
    }
}