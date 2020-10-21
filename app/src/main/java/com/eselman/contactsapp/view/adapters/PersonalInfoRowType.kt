package com.eselman.contactsapp.view.adapters

import com.eselman.contactsapp.model.Address

/**
 * Created by Evangelina Selman
 */

sealed class PhoneType {
    data class Home(val homeNumber: String?): PhoneType()
    data class Work(val workNumber: String?): PhoneType()
    data class Mobile(val mobileNumber: String?): PhoneType()
}

sealed class PersonalInfoRowType {
    data class PhoneInfoRow (val phone: PhoneType?): PersonalInfoRowType()
    data class AddressRow (val address: Address?): PersonalInfoRowType()
    data class BirthDateRow (val birthDate: String?): PersonalInfoRowType()
    data class EmailRow (val email: String?):PersonalInfoRowType()
}