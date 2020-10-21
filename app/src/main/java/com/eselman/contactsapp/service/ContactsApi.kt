package com.eselman.contactsapp.service

import com.eselman.contactsapp.model.Contact
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Evangelina Selman
 */
interface ContactsApi {
    @GET("contacts.json")
    suspend fun getContacts():Response<MutableList<Contact>>
}