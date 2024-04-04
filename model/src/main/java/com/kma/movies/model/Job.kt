package com.kma.movies.model

import java.io.File

data class Job(
    var title: String? = null,
    var descrip: String? = null,
    var tag: String? = null,
    var address: String? = null,
    var responsive: String? = null,
    var salary: String? = null,
    var company: String? = null,
    var benefit: String? = null,
    var exp: String? = null,
    var deadline: String? = null,
    var image: File? = null
)
