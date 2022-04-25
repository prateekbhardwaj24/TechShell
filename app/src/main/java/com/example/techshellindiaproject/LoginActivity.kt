package com.example.techshellindiaproject

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.techshellindiaproject.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        dialog = ProgressDialog(this)
        binding.loginBtn.setOnClickListener {
            dialog.setTitle("Login in process..")
            dialog.show()
            viewModel.postDataUsingVolley(binding.mobileNum.text.toString(),this).observe(this,
                Observer {
                    if (it){
                        startActivity(Intent(this,FormActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this,"Please register ",Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,RegistrationActivity::class.java))
                        finish()
                    }
                    dialog.dismiss()
                })
        }

    }

}