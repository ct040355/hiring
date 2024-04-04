package com.kma.movies.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteUserRegister (
    @SerializedName("name"    ) var name    : String? = null,
    @SerializedName("username" ) var username : String? = null,
    @SerializedName("password" ) var password : String? = null,
    @SerializedName("role" ) var role : String? = null

)