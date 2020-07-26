package com.example.githubusers

import kotlin.properties.Delegates

class ApiResult {
    var total_count : Int =0
    var incomplete_results : Boolean = true
    var items : ArrayList<User> = ArrayList<User>()
    val avatar_url : String = ""


}