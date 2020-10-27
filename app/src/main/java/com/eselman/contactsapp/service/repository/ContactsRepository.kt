package com.eselman.contactsapp.service.repository

import com.eselman.contactsapp.service.ContactsResponseStatus

/**
 * Created by Evangelina Selman
 */
interface ContactsRepository {
    suspend fun getContacts(): ContactsResponseStatus
}