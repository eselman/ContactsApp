package com.eselman.contactsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eselman.contactsapp.model.Contact
import com.eselman.contactsapp.service.ContactsResponseStatus
import com.eselman.contactsapp.service.repository.ContactsRepository
import com.eselman.contactsapp.utils.ContactsHelper
import com.eselman.contactsapp.view.adapters.RecyclerViewRowType
import kotlinx.coroutines.*

/**
 * Created by Evangelina Selman
 */
class ContactViewModel(private val contactsRepository: ContactsRepository?): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<ContactsResponseStatus.Error?>()
    val itemsList = MutableLiveData<List<RecyclerViewRowType>>()

    private var contactsList: MutableList<Contact>? = null

    init {
        getContacts()
    }

    fun getContacts() {
        isLoading.value = true

        if (contactsList.isNullOrEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    when (val response = contactsRepository?.getContacts()) {
                        is ContactsResponseStatus.Success -> {
                            contactsList = response.list
                            itemsList.value = ContactsHelper.createItemsList(contactsList)
                            error.value = null
                            isLoading.value = false
                        }
                        is ContactsResponseStatus.Error -> {
                            error.value = response
                        }
                        else -> {
                            isLoading.value = true
                        }
                    }
                }
            }
        } else {
            itemsList.value = ContactsHelper.createItemsList(contactsList)
            error.value = null
            isLoading.value = false
        }
    }

    fun getContactById(contactId: Long): Contact? {
       var contact: Contact? = null
        contactsList?.let {
           if (it.isNotEmpty() && it.filter { contact -> contact.id == contactId }.isNotEmpty()) {
               contact = it.filter { contact -> contact.id == contactId }[0]
           }
       }
        return contact
    }

    fun updateFavoriteContact(contactId: Long, favorite:Boolean) {
        val contact = getContactById(contactId)
        contact?.apply {
            isFavorite = favorite
            itemsList.value = ContactsHelper.createItemsList(contactsList)
        }
    }
}