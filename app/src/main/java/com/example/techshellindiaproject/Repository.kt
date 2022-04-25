package com.example.techshellindiaproject

import android.content.Context

import android.widget.Toast

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class Repository {
    fun uploadJobData(
        customerId: String,
        customerName: String,
        jobtitle: String,
        jobLocation: String,
        budgetFrom: String,
        budgetTo: String,
        jobtype: String,
        people: String,
        shotDec: String,
        details: String,
        skills: ArrayList<String>,
        experience: String
    ): MutableLiveData<Boolean> {
        var result: MutableLiveData<Boolean> = MutableLiveData()
        val jso: JSONObject = JSONObject()
        val http: HttpURLConnection =
            URL(Config.ADD_NEW_JOB_URL).openConnection() as HttpURLConnection
        CoroutineScope(Dispatchers.IO).launch {
            var jsonrrr = JSONArray()
            for (i in skills) {
                jsonrrr.put(i)
            }
            jso.put("customer_id", customerId)
            jso.put("customer_name", customerName)
            jso.put("job_title", jobtitle)
            jso.put("job_location", jobLocation)
            jso.put("budget_from", budgetFrom)
            jso.put("budget_to", budgetTo)
            jso.put("job_type", jobtype)
            jso.put("people", people)
            jso.put("skills", jsonrrr)
            jso.put("experience", experience)
            jso.put("shot_dec", shotDec)
            jso.put("details", details)

            http.requestMethod = "POST"
            http.doOutput = true
            http.setRequestProperty("Content-Type", "application/json")
            http.connect()

            val wr = DataOutputStream(http.outputStream)
            wr.writeBytes(jso.toString())
            wr.flush()
            wr.close()
            if (http.responseCode == 200) {
                result.postValue(true)
            } else {
                result.postValue(false)
            }
            http.disconnect()
        }
        return result
    }

    fun registerCustomer(
        fullname: String,
        mobile: String,
        address: String,
        context: Context
    ): MutableLiveData<Boolean> {
        var result: MutableLiveData<Boolean> = MutableLiveData()

        val request: StringRequest =
            object : StringRequest(Request.Method.POST, Config.ADD_NEW_CUSTOMER,
                Response.Listener<String?> { response ->

                    if (JSONObject(response).getString("rcode") == "200") {
                        result.postValue(true)
                    } else {
                        result.postValue(false)
                    }
                }, Response.ErrorListener { error -> // method to handle errors.
                    result.postValue(false)
                    Toast.makeText(context, " error reg $error", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()

                    params["full_name"] = fullname
                    params["mobile"] = mobile
                    params["address"] = address
                    params["profile_pic"] = "pic"
                    return params
                }
            }
        // below line is to make
        // a json object request.
        Singleton.getInstance(context).addToRequestQueue(request)
        return result
    }

    fun loginCustomer(mobile: String, context: Context): MutableLiveData<Boolean> {
        var result: MutableLiveData<Boolean> = MutableLiveData()
        val request: StringRequest =
            object : StringRequest(Request.Method.POST, Config.LOGIN_CUSTOMER,
                Response.Listener<String?> { response ->
                    if (JSONObject(response).getBoolean("success") == false) {
                        result.postValue(true)
                    } else {
                        result.postValue(false)
                    }


                }, Response.ErrorListener { error -> // method to handle errors.
                    Toast.makeText(context, " error $error", Toast.LENGTH_SHORT).show()
                    result.postValue(false)
                }) {
                override fun getParams(): Map<String, String>? {

                    val params: MutableMap<String, String> = HashMap()
                    params["mobile"] = mobile

                    return params
                }
            }
        // below line is to make
        // a json object request.
        Singleton.getInstance(context).addToRequestQueue(request)
        return result
    }

    fun loadSkills(context: Context): MutableLiveData<ArrayList<String>> {
        var result: MutableLiveData<ArrayList<String>> = MutableLiveData()

        val skillset: ArrayList<String> = ArrayList()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Config.GET_SKILL_URL, null,
            { response ->

                val skillsNode = response.getJSONArray("data")
                skillset.add("-Choose Skill-")
                for (i in 0 until skillsNode.length()) {
                    val item = skillsNode.getJSONObject(i)
                    skillset.add(item.getString("skills"))
                }
                result.postValue(skillset)

            },
            { error ->
                // TODO: Handle error
            }
        )
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
        return result
    }

    fun loadExper(context: Context): MutableLiveData<java.util.ArrayList<String>> {
        var result: MutableLiveData<ArrayList<String>> = MutableLiveData()

        val experienceSet: ArrayList<String> = ArrayList()
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, Config.GET_EXPER_URL, null,
            { response ->

                val skillsNode = response.getJSONArray("data")
                for (i in 0 until skillsNode.length()) {
                    val item = skillsNode.getJSONObject(i)
                    experienceSet.add(item.getString("experience"))
                }
                result.postValue(experienceSet)
            },
            { error ->
                // TODO: Handle error
            }
        )
        Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest)
        return result
    }

}