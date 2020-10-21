package com.eselman.contactsapp.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eselman.contactsapp.R
import com.eselman.contactsapp.model.Contact
import com.eselman.contactsapp.utils.loadImage
import com.eselman.contactsapp.view.adapters.PersonalInfoAdapter
import com.eselman.contactsapp.view.adapters.PersonalInfoRowType
import com.eselman.contactsapp.view.adapters.PhoneType
import com.eselman.contactsapp.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.fragment_contact_detail.*

/**
 * Created by Evangelina Selman
 */
class ContactDetailFragment : Fragment() {
    private val contactViewModel: ContactViewModel by activityViewModels()
    private var contact: Contact? = null
    private lateinit var menuItem: MenuItem

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_contact_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val contactId = ContactDetailFragmentArgs.fromBundle(it).contactId
            contact = contactViewModel.getContactById(contactId)
            populateContactView()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.contact_detail_menu, menu)
        menuItem = menu.findItem(R.id.mark_as_favorite)
        contact?.let {
            setMenuIcon(menuItem, it.isFavorite)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.mark_as_favorite -> {
                contact?.let {
                    setMenuIcon(item, !it.isFavorite)
                    contactViewModel.updateFavoriteContact(it.id, !it.isFavorite)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateContactView() {
        contact?.apply {
            contactImage.loadImage(largeImageURL, R.mipmap.user_large)
            contactName.text = name
            val personalInfoList: MutableList<PersonalInfoRowType> = mutableListOf()
            phone?.let {phoneInfo ->
                phoneInfo.home?.let { personalInfoList.add(PersonalInfoRowType.PhoneInfoRow(PhoneType.Home(it))) }
                phoneInfo.mobile?.let { personalInfoList.add(PersonalInfoRowType.PhoneInfoRow(PhoneType.Mobile(it))) }
                phoneInfo.work?.let { personalInfoList.add(PersonalInfoRowType.PhoneInfoRow(PhoneType.Work(it))) }
            }

            personalInfoList.add(PersonalInfoRowType.AddressRow(address))
            personalInfoList.add(PersonalInfoRowType.BirthDateRow(birthDate))
            personalInfoList.add(PersonalInfoRowType.EmailRow(emailAddress))

            personalInfoRecyclerView.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = PersonalInfoAdapter(personalInfoList, context)
                addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            }

        }
    }

    private fun setMenuIcon(item: MenuItem, isFavorite:Boolean) {
        when(isFavorite) {
            true -> item.setIcon(R.mipmap.favorite_true)
            false -> item.setIcon(R.mipmap.favorite_false)
        }
    }
}