package com.revakovsky.data.remote

import com.revakovsky.data.BuildConfig
import com.revakovsky.data.remote.dto.BooksOverviewDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ApiService {

    @GET("lists/overview.json")
    suspend fun getBooksOverview(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY,
    ): BooksOverviewDto

}
