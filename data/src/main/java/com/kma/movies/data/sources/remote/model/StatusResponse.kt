package com.kma.movies.data.sources.remote.model

import com.google.gson.annotations.SerializedName


data class StatusResponse (
  @SerializedName("apply_status"   ) var apply_status    : String? = null,
  @SerializedName("status"   ) var status    : Boolean? = null,
)