package com.example.techshellindiaproject

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {

    var selectedSkillsArray: ArrayList<String> = ArrayList()
    var selectedExperience: String = ""
    private val repository = Repository()

    fun uploadJob(
        customerId: String,
        customerName: String,
        jobType: String,
        jobTitle: String,
        jobLocation: String,
        budgetTo: String,
        budgetFrom: String,
        people: String,
        shotDec: String,
        deatails: String
    ): MutableLiveData<Boolean> {
       
        return repository.uploadJobData(
            customerId,
            customerName,
            jobTitle,
            jobLocation,
            budgetFrom,
            budgetTo,
            jobType,
            people,
            shotDec,
            deatails,
            selectedSkillsArray,
            selectedExperience
        )

    }

    fun validation(
        customerId: String,
        customerName: String,
        jobtype: String,
        jobtitle: String,
        jobLocation: String,
        budgetTo: String,
        budgetFrom: String,
        people: String,
        shotDec: String,
        details: String
    ): Boolean {
        return if (customerId.isEmpty() && customerName
                .isEmpty() && jobtitle.isEmpty() && jobLocation
                .isEmpty() && budgetFrom.isEmpty()
            && budgetTo.isEmpty() && jobtype.isEmpty() && people
                .isEmpty() && shotDec.isEmpty() && details
                .isEmpty() && details.isEmpty()
        ) {
            false
        } else if (customerId.isEmpty()) {
            false
        } else if (customerName.isEmpty()) {
            false
        } else if (jobtitle.isEmpty()) {
            false
        } else if (budgetFrom.isEmpty()) {
            false
        } else if (budgetTo.isEmpty()) {
            false
        } else if (jobtype.isEmpty()) {
            false
        } else if (people.isEmpty()) {
            false
        } else if (shotDec.isEmpty()) {
            false
        } else if (details.isEmpty()) {
            false
        }else if (selectedSkillsArray.isEmpty()){
            false
        }
        else {
            true
        }
    }

    fun getSkills(formActivity: FormActivity): MutableLiveData<ArrayList<String>> {
        return repository.loadSkills(formActivity as Context)
    }

    fun getExper(formActivity: FormActivity): MutableLiveData<ArrayList<String>> {
        return repository.loadExper(formActivity as Context)
    }

}