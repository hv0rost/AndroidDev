package ru.rsue.borisov.retrofit

import com.google.gson.annotations.SerializedName

class Post(val id : Int, val title : String?, val text : String?) {
    private val userId = 0
    //val id = 0
    //val title: String? = null

/*    @SerializedName("body")
    val text: String? = null*/

    fun getUserId():Int{
        return userId
    }
}
