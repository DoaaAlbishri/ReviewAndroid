package com.example.review

import com.google.gson.annotations.SerializedName

class User {
    var data: List<UserDetails>? = null

    class UserDetails {

        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        @SerializedName("pk")
        var pk: Int? = null

        constructor(pk: Int?, name: String?, location: String?) {
            this.pk = pk
            this.name = name
            this.location = location
        }
    }
}