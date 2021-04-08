package com.akih.submission2bfaa

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akih.submission2bfaa.Utils.RetrofitGithub
import com.akih.submission2bfaa.data.ListUser
import com.akih.submission2bfaa.data.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<Users>>()

    fun setSearchUsers(query : String){
        RetrofitGithub.apiInstance
            .getSearchUsers(query)
            .enqueue(object : Callback<ListUser>{
                override fun onFailure(call: Call<ListUser>, t: Throwable) {
                    Log.d("onFailure", "$t.message")
                }

                override fun onResponse(call: Call<ListUser>, response: Response<ListUser>) {
                    if (response.isSuccessful){
                        listUsers.postValue(response.body()?.items)
                    }
                }

            })
    }

    fun getSearchUsers(): LiveData<ArrayList<Users>>{
        return listUsers
    }
}