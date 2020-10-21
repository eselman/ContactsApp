package com.eselman.contactsapp.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Evangelina Selman
 */
data class Contact (
    val id: Long,
    val name:String?,
    val companyName:String?,
    var isFavorite:Boolean = false,
    val smallImageURL: String?,
    val largeImageURL: String?,
    val emailAddress: String?,
    @SerializedName("birthdate")
    val birthDate: String?,
    val phone: Phone?,
    val address: Address?
)