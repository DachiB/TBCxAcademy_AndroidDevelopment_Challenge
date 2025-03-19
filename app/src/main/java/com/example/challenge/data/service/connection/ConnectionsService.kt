package com.example.challenge.data.service.connection

import com.example.challenge.data.model.connection.ConnectionDto
import retrofit2.Response
import retrofit2.http.GET

interface ConnectionsService {
    @GET("34735e57-9a1f-4e12-b35a-34fd8462b0de")
    suspend fun getConnections(
    ): Response<List<ConnectionDto>>
}