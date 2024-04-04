package com.kma.movies.model

data class JobResponse(
    var idJob: String? = null,
    var createBy: String? = null,
    var title: String? = null,
    var descrip: String? = null,
    var tag: String? = null,
    var address: String? = null,
    var approved: Boolean? = null,
    var exp: String? = null,
    var responsive: String? = null,
    var salary: String? = null,
    var company: String? = null,
    var deadline: String? = null,
    var id: String? = null,
    var benefit: String? = null,
    var image: String? = null,
    var applied: ArrayList<String> = arrayListOf(),
    var qualify: String? = null
)
