package com.eselman.contactsapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Evangelina Selman
 */
object ContactsApiService {
    private const val baseUrl = "https://s3.amazonaws.com/technical-challenge/v3/"

    fun getContactsService(): ContactsApi {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ContactsApi::class.java)
    }
}