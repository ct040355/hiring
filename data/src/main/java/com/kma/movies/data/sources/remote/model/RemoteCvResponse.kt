package com.kma.movies.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCvResponse (
    @SerializedName("username"    ) var username    : String?           = null,
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("education"   ) var education   : String?           = null,
    @SerializedName("experience"  ) var experience  : String?           = null,
    @SerializedName("salary"      ) var salary      : String?           = null,
    @SerializedName("location"    ) var location    : String?           = null,
    @SerializedName("gender"      ) var gender      : String?           = null,
    @SerializedName("type"        ) var type        : String?           = null,
    @SerializedName("qualify"     ) var qualify     : String?           = null,
    @SerializedName("exp"         ) var exp         : String?           = null,
    @SerializedName("tag"         ) var tag         : String?           = null,
    @SerializedName("image"       ) var image       : String?           = null,
    @SerializedName("applying"    ) var applying    : ArrayList<String> = arrayListOf(),
    @SerializedName("phone"       ) var phone       : String?           = null,
    @SerializedName("cv"          ) var cv          : String?           = null
)