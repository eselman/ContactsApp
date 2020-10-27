package com.eselman.contactsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.eselman.contactsapp.R
import kotlinx.android.synthetic.main.fragment_contact_error.*

/**
 * Created by Evangelina Selman
 */
class ContactErrorFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contact_error, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorMessageTextView.text = getString(R.string.contacts_error_message)
        retryButton.setOnClickListener {
            val action = ContactErrorFragmentDirections.actionGoToListFromError(true)
            findNavController(this).navigate(action)
        }
    }
}