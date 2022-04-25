package com.example.techshellindiaproject

import android.R.layout
import android.R.layout.simple_spinner_item
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.techshellindiaproject.databinding.ActivityFormBinding

class FormActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFormBinding
    private var skillArray: ArrayList<String> = ArrayList()
    private var testArray: ArrayList<Int> = ArrayList()
    private lateinit var viewModel: FormViewModel
    private lateinit var dialog: ProgressDialog
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(FormViewModel::class.java)
        dialog = ProgressDialog(this)
        dialog.setTitle("Loading...")
        dialog.show()
        binding.submitBtn.setOnClickListener {

            dialog.setTitle("Posting in process...")
            dialog.show()
            if (viewModel.validation(
                    binding.customerId.text.toString(),
                    binding.customerName.text.toString(),
                    binding.jobType.text.toString(),
                    binding.jobTitle.text.toString(),
                    binding.jobLocation.text.toString(),
                    binding.budgetTo.text.toString(),
                    binding.budgetFrom.text.toString(),
                    binding.people.text.toString(),
                    binding.shotDec.text.toString(),
                    binding.deatails.text.toString()
            )) {
                viewModel.uploadJob(
                    binding.customerId.text.toString(),
                    binding.customerName.text.toString(),
                    binding.jobType.text.toString(),
                    binding.jobTitle.text.toString(),
                    binding.jobLocation.text.toString(),
                    binding.budgetTo.text.toString(),
                    binding.budgetFrom.text.toString(),
                    binding.people.text.toString(),
                    binding.shotDec.text.toString(),
                    binding.deatails.text.toString()

                ).observe(this, Observer {
                    it?.let {
                        if (it) {
                            Toast.makeText(this, "Job Posted successfully", Toast.LENGTH_SHORT)
                                .show()
                            binding.customerId.text!!.clear()
                                binding.customerName.text!!.clear()
                                binding.jobType.text!!.clear()
                                binding.jobTitle.text!!.clear()
                                binding.jobLocation.text!!.clear()
                                binding.budgetTo.text!!.clear()
                                binding.budgetFrom.text!!.clear()
                                binding.people.text!!.clear()
                                binding.shotDec.text!!.clear()
                                binding.deatails.text!!.clear()
                            binding.skillsLayout.removeAllViews()
                            binding.skills.setSelection(0)
                            binding.experience.setSelection(0)
                        } else {
                            Toast.makeText(this, "Job Posted failure", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.dismiss()
                })
            } else {
                dialog.dismiss()
                Toast.makeText(this, "Please fill all values", Toast.LENGTH_SHORT).show()
            }

        }
        viewModel.getSkills(this).observe(this, Observer {
            dialog.dismiss()
            val spinnerArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(this, simple_spinner_item, it)
            spinnerArrayAdapter.setDropDownViewResource(layout.simple_spinner_dropdown_item) // The drop down view
            binding.skills.adapter= spinnerArrayAdapter
            testArray.add(0)
            binding.skills.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (!testArray.contains(p2)) {
                        val textView1 = TextView(this@FormActivity)
                        textView1.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        textView1.text = it[p2]
                        binding.skillsLayout.addView(textView1)
                        testArray.add(p2)
                        viewModel.selectedSkillsArray.add(it[p2])
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // TODO("Not yet implemented")
                }

            }
        })
        viewModel.getExper(this).observe(this, Observer {
            if (dialog.isShowing){
                dialog.dismiss()
            }
            val spinnerArrayAdapter1: ArrayAdapter<String> =
                ArrayAdapter<String>(this, simple_spinner_item, it)
            spinnerArrayAdapter1.setDropDownViewResource(layout.simple_spinner_dropdown_item) // The drop down view
            binding.experience.adapter = spinnerArrayAdapter1
            binding.experience.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        viewModel.selectedExperience = it[p2]
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        // TODO("Not yet implemented")
                    }

                }

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}