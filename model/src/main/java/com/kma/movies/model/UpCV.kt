package com.kma.movies.model

import java.io.File


data class UpCV (
  var username    : String?           = null,
  var name        : String?           = null,
  var description : String?           = null,
  var education   : ArrayList<Education>?           = null,
  var experience  : ArrayList<Experience>?           = null,
  var salary      : String?           = null,
  var location    : String?           = null,
  var gender      : String?           = null,
  var type        : String?           = null,
  var qualify     : String?           = null,
  var exp         : String?           = null,
  var tag         : String?           = null,
  var image       : File?           = null,

  )
data class Education (
  var des    : String,
  var from   : String,
  var qua    : String,
  var school : String,
  var to     : String
)

data class Experience (
  var pos  : String? = null,
  var from : String? = null,
  var des  : String? = null,
  var com  : String? = null,
  var to   : String? = null
)