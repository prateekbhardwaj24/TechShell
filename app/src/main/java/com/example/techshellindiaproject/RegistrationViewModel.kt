package com.example.techshellindiaproject

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrationViewModel:ViewModel() {
    val repository = Repository()
    fun postDataUsingVolley(
        fullname: String,
        mobile: String,
        address: String,
        registrationActivity: RegistrationActivity
    ): MutableLiveData<Boolean> {
        return repository.registerCustomer(fullname,mobile,address,registrationActivity as Context)
    }
}