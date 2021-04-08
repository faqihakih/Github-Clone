package com.akih.submission2bfaa.Utils

import com.akih.submission2bfaa.data.DetailUser
import com.akih.submission2bfaa.data.ListUser
import com.akih.submission2bfaa.data.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Utils {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUsers(
        @Query("q") query: String
    ):Call<ListUser>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUsers(
            @Path("username") username : String
    ) : Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowerUsers(
            @Path("username") username : String
    ) : Call<ArrayList<Users>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowingUsers(
            @Path("username") username : String
    ) : Call<ArrayList<Users>>
}