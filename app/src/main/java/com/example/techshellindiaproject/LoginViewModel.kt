package com.example.techshellindiaproject

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel:ViewModel() {
    val repository = Repository()
    fun postDataUsingVolley(mobile: String, loginActivity: LoginActivity): MutableLiveData<Boolean> {
        return repository.loginCustomer(mobile,loginActivity as Context)
    }
}