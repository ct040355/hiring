package com.kma.movies.data.sources.remote.model

import com.google.gson.annotations.SerializedName


data class RemoteResponse (
  @SerializedName("status"   ) var status    : Boolean? = null,
)