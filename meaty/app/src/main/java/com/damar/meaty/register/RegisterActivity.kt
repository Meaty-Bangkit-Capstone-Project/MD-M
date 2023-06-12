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
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
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

            val name = binding.edRegisterName.text.toString()
            val username = binding.edRegisterUsername.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val gender = binding.spinnerGender.selectedItem.toString()
            val domisili = binding.edRegisterDomisili.text.toString()
            val ageText = binding.edRegisterAge.text.toString()
            val age = if (ageText.isNotEmpty()) {
                ageText.toIntOrNull() ?: 0
            } else {
                0
            }
            val work = binding.edRegisterWork.text.toString()

            when {
                name.isEmpty() || username.isEmpty() || gender.isEmpty() || domisili.isEmpty() || age == 0 || work.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(this, R.string.error_field_empty, Toast.LENGTH_SHORT).show()
                }
                password.length < 8 -> {
                    Toast.makeText(this, R.string.error_minimum_password, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    //nanti jangan lupa tambahkan pengiriman gender, domisili, umur, dan work
                    regisViewModel.createUser(name, username, password)
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

