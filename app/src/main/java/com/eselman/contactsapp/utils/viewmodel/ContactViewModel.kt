package com.eselman.contactsapp.utils.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eselman.contactsapp.R
import com.eselman.contactsapp.model.Contact
import com.eselman.contactsapp.service.ContactsApiService
import com.eselman.contactsapp.view.adapters.RecyclerViewRowType
import kotlinx.coroutines.*

/**
 * Created by Evangelina Selman
 */
class ContactViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val isError = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String?>()
    val itemsList = MutableLiveData<List<RecyclerViewRowType>>()

    private val contactsApiService = ContactsApiService.getContactsService()

    private var job:Job? = null
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        runBlocking(Dispatchers.Main) {
            onError("Exception: ${throwable.localizedMessage}")
        }
    }

    private var contactsList: MutableList<Contact>? = null

    fun getContacts() {
        isLoading.value = true

        if (contactsList.isNullOrEmpty()) {
            job = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler).launch {
                val contactsResponse = contactsApiService.getContacts()
                withContext(Dispatchers.Main) {
                    if (contactsResponse.isSuccessful) {
                        contactsList = contactsResponse.body()
                        itemsList.value = createItemsList()
                        isError.value = false
                        errorMessage.value = null
                        isLoading.value = false
                    } else {
                        onError("Error: ${contactsResponse.message()}")
                    }
                }
            }
        } else {
            itemsList.value = createItemsList()
            isError.value = false
            errorMessage.value = null
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
            createItemsList()
        }
    }

    private fun createItemsList(): List<RecyclerViewRowType> {
        val newItemsList = mutableListOf<RecyclerViewRowType>()
        contactsList?.let {list ->
            if(list.isNotEmpty()) {
                val favoriteContacts = list.filter { it.isFavorite }.sortedBy { it.name }
                if (favoriteContacts.isNotEmpty()) {
                    // Add Favorite Header
                    newItemsList.add(RecyclerViewRowType.Header(R.string.favorite_contacts_header_title))
                    // Add Favorite Contacts
                    for(contact in favoriteContacts) {
                        val contactRow = RecyclerViewRowType.ContactRow(contact)
                        newItemsList.add(contactRow)
                    }
                }

                val nonFavoriteContacts = list.filter { !it.isFavorite  }.sortedBy { it.name }
                if(nonFavoriteContacts.isNotEmpty()) {
                    // Add Non Favorite Header
                    newItemsList.add(RecyclerViewRowType.Header(R.string.non_favorite_contacts_header_title))
                    // Add non favorite contacts
                    for(contact in nonFavoriteContacts) {
                        val contactRow = RecyclerViewRowType.ContactRow(contact)
                        newItemsList.add(contactRow)
                    }
                }
            }
        }

        return newItemsList
    }

    private fun onError(message: String?) {
        errorMessage.value = message
        isError.value = true
        isLoading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}