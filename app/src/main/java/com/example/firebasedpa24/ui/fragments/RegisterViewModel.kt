package com.example.firebasedpa24.ui.fragments

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.firebasedpa24.database.CustomerEntity
import com.example.firebasedpa24.database.CustomerRepository

class RegisterViewModel(application: Application)
    : AndroidViewModel(application) {
        private var repository = CustomerRepository(application)
        fun saveCustomer(customerEntity: CustomerEntity){
            repository.insert(customerEntity)
        }

}