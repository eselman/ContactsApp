package com.eselman.contactsapp.app

import android.app.Application
import com.eselman.contactsapp.BuildConfig
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Created by Evangelina Selman
 */
class ContactsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}