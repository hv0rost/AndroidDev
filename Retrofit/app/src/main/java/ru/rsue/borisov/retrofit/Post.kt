package ru.rsue.borisov.retrofit

import com.google.gson.annotations.SerializedName

class Post(val userid : Int, val title : String?, val text : String?) {
    private val id = 0
    //val id = 0
    //val title: String? = null

/*    @SerializedName("body")
    val text: String? = null*/

    fun getId():Int{
        return id
    }
}
