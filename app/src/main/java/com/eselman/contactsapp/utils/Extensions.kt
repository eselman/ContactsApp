package com.eselman.contactsapp.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Evangelina Selman
 */
fun ImageView.loadImage(uri: String?, defaultResource: Int) {
    val options = RequestOptions()
        .error(defaultResource)
        .placeholder(defaultResource)
    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}