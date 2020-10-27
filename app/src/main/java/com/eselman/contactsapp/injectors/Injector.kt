package com.eselman.contactsapp.injectors

import com.eselman.contactsapp.service.repository.ContactsRepository
import com.eselman.contactsapp.service.repository.ContactsRepositoryImpl

/**
 * Created by Evangelina Selman
 */
object Injector {
    private  var contactsRepository: ContactsRepositoryImpl? = null
    fun provideRepository(): ContactsRepository? {
        if(contactsRepository == null) {
            contactsRepository = ContactsRepositoryImpl()
        }
        return contactsRepository
    }
}