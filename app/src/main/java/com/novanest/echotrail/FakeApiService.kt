package com.novanest.echotrail

import retrofit2.Response
import retrofit2.http.GET

interface FakeApiService {
    @GET("users/2")
    suspend fun getUser(): Response<UserResponse>
}
