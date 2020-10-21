package com.eselman.contactsapp.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.eselman.contactsapp.R
import com.eselman.contactsapp.utils.loadImage
import com.eselman.contactsapp.view.ContactsListFragmentDirections
import kotlinx.android.synthetic.main.contact_item_header.view.*
import kotlinx.android.synthetic.main.contact_item_view.view.*
import java.util.*

/**
 * Created by Evangelina Selman
 */
class ContactsAdapter(var itemsList: MutableList<RecyclerViewRowType>, private val context: Context):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val  headerViewType = 0
    private val contactViewType = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == headerViewType) {
            return HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_header, parent, false), context)
        }
        return ContactsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item_view, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(val item = itemsList[holder.adapterPosition]) {
            is RecyclerViewRowType.Header -> (holder as HeaderViewHolder).bind(item)
            is RecyclerViewRowType.ContactRow -> (holder as ContactsViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int) = when(itemsList[position]) {
        is RecyclerViewRowType.Header -> headerViewType
        is RecyclerViewRowType.ContactRow -> contactViewType
    }

    override fun getItemCount() = itemsList.size

    fun updateItemsList(newItems: List<RecyclerViewRowType>) {
        itemsList.clear()
        itemsList.addAll(newItems)
        notifyDataSetChanged()
    }

    class HeaderViewHolder(headerView: View, private val context: Context): RecyclerView.ViewHolder(headerView) {
        private val headerTitleTextView = headerView.headerTitleTextView
        fun bind(header: RecyclerViewRowType.Header) {
            headerTitleTextView.text = context.getString(header.titleKey).toUpperCase(Locale.getDefault())
        }
    }
    class ContactsViewHolder(private val contactView: View): RecyclerView.ViewHolder(contactView) {
        private val contactImageView = contactView.contactImageView
        private val favoriteImageView = contactView.favoriteImageView
        private val contactNameTextView = contactView.contactNameTextView
        private val companyNameTextView = contactView.companyNameTextView
        private val contactLayout = contactView.contactLayout

        fun bind(contactRow: RecyclerViewRowType.ContactRow) {
            contactRow.contact.apply {
                contactImageView.loadImage(smallImageURL, R.mipmap.user_small)

                if (isFavorite) {
                    favoriteImageView.setImageResource(R.mipmap.favorite_true)
                    favoriteImageView.visibility = View.VISIBLE
                } else {
                    favoriteImageView.visibility = View.INVISIBLE
                }

                contactNameTextView.text = name
                companyNameTextView.text = companyName
                contactLayout.setOnClickListener {
                    val action = ContactsListFragmentDirections.actionGoToContactDetails(id)
                    Navigation.findNavController(contactView).navigate(action)
                }
            }
        }
    }
}