package com.example.challenge.data.model.connection

import com.squareup.moshi.Json

data class ConnectionDto(
    val id: Int,
    val avatar: String? = null,
    val email: String? = null,
    @Json(name = "first_name")
    val firstName: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null
)