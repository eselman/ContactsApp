package com.eselman.contactsapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eselman.contactsapp.model.Contact
import com.eselman.contactsapp.service.ContactsResponseStatus
import com.eselman.contactsapp.service.repository.ContactsRepository
import com.eselman.contactsapp.utils.ContactsHelper
import com.eselman.contactsapp.utils.TestCoroutineRule
import com.eselman.contactsapp.view.adapters.RecyclerViewRowType
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Evangelina Selman
 */
@RunWith(MockitoJUnitRunner::class)
class ContactViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var contactsRepository: ContactsRepository

    @Mock
    private lateinit var itemsListObserver: Observer<List<RecyclerViewRowType>>

    @Mock
    private lateinit var errorObserver: Observer<ContactsResponseStatus.Error?>

    @Test
    fun `test get contacts successfully`() {
        val viewModel = ContactViewModel(contactsRepository)
        testCoroutineRule.runBlockingTest {
            val mockedContactsList = mutableListOf<Contact>()
            mockedContactsList.add(Contact(13, "Miss Piggy", "Muppets, Baby", false, null, null, null, null, null, null))
            mockedContactsList.add(Contact(4, "Harry Potter", null, true, null, null, null, null, null, null))
            doReturn(ContactsResponseStatus.Success(mockedContactsList))
                .`when`(contactsRepository)
                .getContacts()

            viewModel.getContacts()
            viewModel.itemsList.observeForever(itemsListObserver)
            verify(itemsListObserver).onChanged(ContactsHelper.createItemsList(mockedContactsList))
            viewModel.itemsList.removeObserver(itemsListObserver)
        }
    }

    @Test
    fun `test get contacts error`() {
        val viewModel = ContactViewModel(contactsRepository)
        testCoroutineRule.runBlockingTest {
            doReturn(ContactsResponseStatus.Error(""))
                .`when`(contactsRepository)
                .getContacts()
            viewModel.getContacts()
            viewModel.error.observeForever(errorObserver)
            verify(errorObserver).onChanged(ContactsResponseStatus.Error(""))
            viewModel.error.removeObserver(errorObserver)
        }
    }
}