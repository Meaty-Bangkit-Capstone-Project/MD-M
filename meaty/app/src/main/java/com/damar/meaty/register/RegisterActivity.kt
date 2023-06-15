package com.damar.meaty.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.customview.FormValidator
import com.damar.meaty.databinding.ActivityRegisterBinding
import com.damar.meaty.login.LoginActivity
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val genderOptions = arrayOf(getString(R.string.gender_male), getString(R.string.gender_female))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        val regisViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RegisterViewModel::class.java]

        regisViewModel.infoError.observe(this) { error ->
            showLoading(false)
            buttonOn(true)

            when (error) {
                true -> {
                    val errorMessage = regisViewModel.errorMessage.value
                    Toast.makeText(this, getString(R.string.error_register), Toast.LENGTH_SHORT).show()
                }
                false -> {
                    val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    Snackbar.make(binding.root, getString(R.string.registration_success), Snackbar.LENGTH_SHORT).show()
                    startActivity(loginIntent)
                    finish()
                }
            }
        }

        binding.txtLogin.setOnClickListener {
            val signUpIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(signUpIntent)
        }

        binding.btnRegister.setOnClickListener {
            if (!FormValidator.isValidRegister(binding)) {
                return@setOnClickListener
            }

            val username = binding.edRegisterUsername.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val firstName = binding.edRegisterFirstName.text.toString()
            val lastName = binding.edRegisterLastName.text.toString()
            val domisili = binding.edRegisterDomisili.text.toString()
            val work = binding.edRegisterWork.text.toString()
            val ageText = binding.edRegisterAge.text.toString()
            val age = if (ageText.isNotEmpty()) {
                ageText.toIntOrNull() ?: 0
            } else {
                0
            }
            val gender = binding.spinnerGender.selectedItem.toString()

            when {
                username.isEmpty() || password.isEmpty() || email.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || domisili.isEmpty() || work.isEmpty()  || age == 0|| gender.isEmpty() -> {
                    Toast.makeText(this, R.string.error_field_empty, Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(this, R.string.error_minimum_password, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //nanti jangan lupa tambahkan pengiriman gender, domisili, umur, dan work
                    regisViewModel.createUser(username, password, email, firstName, lastName, domisili, work, age, gender)
                    showLoading(true)
                    buttonOn(false)
                }
            }
        }

        AnimationUtil.playRegisAnimation(binding)
    }

    private fun buttonOn(state: Boolean) {
        binding.btnRegister.isEnabled = state
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarRegister.visibility = if (state) View.VISIBLE else View.GONE
    }
}

