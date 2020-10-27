package com.eselman.contactsapp.utils

import com.eselman.contactsapp.R
import com.eselman.contactsapp.model.Contact
import com.eselman.contactsapp.view.adapters.RecyclerViewRowType

/**
 * Created by Evangelina Selman
 */
object ContactsHelper {
   fun createItemsList(contactsList: MutableList<Contact>?): List<RecyclerViewRowType> {
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
}
