package com.eselman.contactsapp.view.adapters

import com.eselman.contactsapp.model.Contact

/**
 * Created by Evangelina Selman
 */
sealed class RecyclerViewRowType {
    data class Header(val titleKey: Int): RecyclerViewRowType()
    data class ContactRow (val contact: Contact): RecyclerViewRowType()
}