package com.eselman.contactsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eselman.contactsapp.service.repository.ContactsRepository

/**
 * Created by Evangelina Selman
 */
class ContactViewModelFactory(private val contactRepository: ContactsRepository?): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ContactViewModel(contactRepository) as T
}