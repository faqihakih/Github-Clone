package com.akih.submission2bfaa.data

data class DetailUser (
        val login : String,
        val id : Int,
        val name : String,
        val avatar_url : String,
        val public_repos : Int,
        val followers_url : String,
        val following_url : String,
        val followers : Int,
        val following : Int,
        val company : String,
        val location : String
)