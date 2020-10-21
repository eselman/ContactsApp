package com.eselman.contactsapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eselman.contactsapp.R
import kotlinx.android.synthetic.main.personal_info_view.view.*
import java.util.*

/**
 * Created by Evangelina Selman
 */
class PersonalInfoAdapter(private val personalInfoList: MutableList<PersonalInfoRowType>, private val context: Context):RecyclerView.Adapter<PersonalInfoAdapter.PersonalInfoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.personal_info_view, parent, false)
        return PersonalInfoViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: PersonalInfoViewHolder, position: Int) {
        holder.bind(personalInfoList[position])
    }

    override fun getItemCount() = personalInfoList.size

    class PersonalInfoViewHolder(personalInfoView: View, private val context:Context): RecyclerView.ViewHolder(personalInfoView) {
        private val labelInfoType = personalInfoView.labelInfoType
        private val infoValue = personalInfoView.infoValue
        private val infoValueType = personalInfoView.infoValueType

        fun bind(personalInfoRowType: PersonalInfoRowType) {
            when (personalInfoRowType) {
                is PersonalInfoRowType.PhoneInfoRow -> {
                    labelInfoType.text = context.getString(R.string.label_info_type_phone).toUpperCase(
                        Locale.getDefault())
                    when(val phoneType = personalInfoRowType.phone){
                        is PhoneType.Work -> {
                            infoValue.text = phoneType.workNumber
                            infoValueType.text = context.getString(R.string.phone_type_work).toUpperCase(Locale.getDefault())
                        }
                        is PhoneType.Home -> {
                            infoValue.text = phoneType.homeNumber
                            infoValueType.text = context.getString(R.string.phone_type_home).toUpperCase(Locale.getDefault())
                        }
                        is PhoneType.Mobile -> {
                            infoValue.text = phoneType.mobileNumber
                            infoValueType.text = context.getString(R.string.phone_type_mobile).toUpperCase(Locale.getDefault())
                        }
                    }
                }

                is PersonalInfoRowType.AddressRow -> {
                    labelInfoType.text = context.getString(R.string.label_info_type_address).toUpperCase(
                        Locale.getDefault())
                    val address = personalInfoRowType.address
                    address?.apply {
                        infoValue.text = "$street, $city, $zipCode, $state"
                    }
                    infoValueType.visibility = View.GONE
                }

                is PersonalInfoRowType.BirthDateRow -> {
                    labelInfoType.text = context.getString(R.string.label_info_type_birth_date).toUpperCase(
                        Locale.getDefault())
                    infoValue.text = personalInfoRowType.birthDate
                    infoValueType.visibility = View.GONE
                }

                is PersonalInfoRowType.EmailRow -> {
                    labelInfoType.text = context.getString(R.string.label_info_type_email).toUpperCase(
                        Locale.getDefault())
                    infoValue.text = personalInfoRowType.email
                    infoValueType.visibility = View.GONE
                }
            }
        }
    }
}