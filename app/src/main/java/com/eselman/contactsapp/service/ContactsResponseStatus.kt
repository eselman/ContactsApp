package com.eselman.contactsapp.service

import com.eselman.contactsapp.model.Contact

/**
 * Created by Evangelina Selman
 */
sealed class ContactsResponseStatus {
    data class Success(val list: MutableList<Contact>?): ContactsResponseStatus()
    data class Error(val errorMessage: String?): ContactsResponseStatus()
}