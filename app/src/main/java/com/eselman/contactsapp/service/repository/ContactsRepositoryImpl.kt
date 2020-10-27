package com.eselman.contactsapp.service.repository

import com.eselman.contactsapp.service.ContactsApi
import com.eselman.contactsapp.service.ContactsApiService
import com.eselman.contactsapp.service.ContactsResponseStatus

/**
 * Created by Evangelina Selman
 */
class ContactsRepositoryImpl(private val contactsApiService: ContactsApi = ContactsApiService.getContactsService()): ContactsRepository {
    override suspend fun getContacts(): ContactsResponseStatus {
        return  try {
            val contactsResponse = contactsApiService.getContacts()
            when {
                contactsResponse.isSuccessful -> ContactsResponseStatus.Success(contactsResponse.body())
                else -> ContactsResponseStatus.Error(contactsResponse.message())
            }
        } catch (e: Exception) {
            ContactsResponseStatus.Error(e.localizedMessage)
        }
    }
}