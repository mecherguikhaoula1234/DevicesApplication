package com.example.managedevices.Network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder

class RetrofitBuilder {

    /**
     * Create an instance of retrofit to the base URL
     */
    fun createRetrofitInstance() : Retrofit {
        val gson = GsonBuilder().setLenient().create()

        retrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofitInstance
    }


    //---------------------------------------------------------------------
    //  Members
    //---------------------------------------------------------------------

    private var baseUrl: String = "https://526fe7b9-9969-4f9d-b3e5-c04c7fbd4ae0.mock.pstmn.io/"
    private lateinit var retrofitInstance: Retrofit
}