package com.kma.movies.model


data class CvResponse (
    var username    : String?           = null,
    var name        : String?           = null,
    var description : String?           = null,
    var education   : String?           = null,
    var experience  : String?           = null,
    var salary      : String?           = null,
    var location    : String?           = null,
    var gender      : String?           = null,
    var type        : String?           = null,
    var qualify     : String?           = null,
    var exp         : String?           = null,
    var tag         : String?           = null,
    var image       : String?           = null,
    var applying    : ArrayList<String> = arrayListOf(),
    var phone       : String?           = null,
    var cv          : String?           = null
)