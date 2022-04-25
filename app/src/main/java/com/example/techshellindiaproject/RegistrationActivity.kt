package com.example.techshellindiaproject
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.techshellindiaproject.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel
    private lateinit var dialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)
        binding.submitBtn.setOnClickListener {
            dialog = ProgressDialog(this)
            dialog.setTitle("Registration in process..")
            dialog.show()
            viewModel.postDataUsingVolley(
                binding.fullNameTxt.text.toString(),
                binding.mobileTxt.text.toString(),
                binding.addressTxt.text.toString(),
                this
            ).observe(this, Observer {
                if (it){
                    Toast.makeText(this,"Registered successfully",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this,"already registered",Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            })
        }

    }

}