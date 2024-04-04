package com.kma.movies.data.sources.remote.model
import com.google.gson.annotations.SerializedName
data class RemoteJobResponse (
    @SerializedName("_id"        ) var idJob         : String?           = null,
    @SerializedName("create_by"  ) var createBy   : String?           = null,
    @SerializedName("title"      ) var title      : String?           = null,
    @SerializedName("descrip"    ) var descrip    : String?           = null,
    @SerializedName("tag"        ) var tag        : String?           = null,
    @SerializedName("address"    ) var address    : String?           = null,
    @SerializedName("approved"   ) var approved   : Boolean?          = null,
    @SerializedName("exp"        ) var exp        : String?           = null,
    @SerializedName("responsive" ) var responsive : String?           = null,
    @SerializedName("salary"     ) var salary     : String?           = null,
    @SerializedName("company"    ) var company    : String?           = null,
    @SerializedName("deadline"   ) var deadline   : String?           = null,
    @SerializedName("id"         ) var id         : String?           = null,
    @SerializedName("benefit"    ) var benefit    : String?           = null,
    @SerializedName("image"      ) var image      : String?           = null,
    @SerializedName("applied"    ) var applied    : ArrayList<String> = arrayListOf(),
    @SerializedName("qualify"    ) var qualify    : String?           = null

)