package com.eselman.contactsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.eselman.contactsapp.R
import com.eselman.contactsapp.view.adapters.ContactsAdapter
import com.eselman.contactsapp.utils.viewmodel.ContactViewModel
import kotlinx.android.synthetic.main.fragment_contacts_list.*
import timber.log.Timber

/**
 * Created by Evangelina Selman
 */
class ContactsListFragment : Fragment() {
    private  val contactViewModel: ContactViewModel by activityViewModels()
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactViewModel.getContacts()

        contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            contactsAdapter = ContactsAdapter(mutableListOf(), context)
            adapter = contactsAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
        registerObservers()
    }

    private fun registerObservers() {
        contactViewModel.itemsList.observe(viewLifecycleOwner, { contacts ->
            contacts?.let {
                contactsRecyclerView.visibility = View.VISIBLE
                contactsAdapter.updateItemsList(contacts)
                Timber.d("Contacts Loaded Successfully")
            }
        })

        contactViewModel.isError.observe(viewLifecycleOwner, {isError ->
            if (isError) {
                val action = ContactsListFragmentDirections.actionGoToError()
                findNavController(this).navigate(action)
            }
        })

        contactViewModel.errorMessage.observe(viewLifecycleOwner, {
            Timber.e("Exception loading contacts: $it")
        })

        contactViewModel.isLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                loadingView.visibility = if(it) View.VISIBLE else View.GONE
                if(it) {
                    contactsRecyclerView.visibility = View.GONE
                }
            }
        })
    }
}