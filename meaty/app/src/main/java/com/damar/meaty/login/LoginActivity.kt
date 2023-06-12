package com.damar.meaty.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.damar.meaty.MainActivity
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.customview.FormValidator
import com.damar.meaty.databinding.ActivityLoginBinding
import com.damar.meaty.home.HomeFragment.Companion.USER_TOKEN
import com.damar.meaty.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val sharedPref = this.getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
        var usertoken = sharedPref.getString(getString(R.string.user_token), "")
        when {
            usertoken != "" -> {
                USER_TOKEN = usertoken
                moveToHome()
                finish()
            }
            else -> {
                val loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]
                loginViewModel.infoError.observe(this) { error ->
                    when (error) {
                        true -> {
                            Toast.makeText(this, getString(R.string.error_incorrect_email_password), Toast.LENGTH_SHORT).show()
                            showLoading(false)
                            buttonOn(true)
                        }
                        false -> {
                            getUserInfo(sharedPref)
                            showLoading(false)
                            buttonOn(true)
                            moveToHome()
                            finish()
                        }
                    }
                }

                binding.txtSignUp.setOnClickListener {
                    val signUpIntent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(signUpIntent)
                }

                binding.btnLogin.setOnClickListener {
                    if (FormValidator.isValidLogin(binding)) {
                        val username = binding.edLoginUsername.text.toString()
                        val password = binding.edLoginPassword.text.toString()
                        buttonOn(false)
                        checkLogin(username, password)
                        showLoading(true)
                        when {
                            username.isEmpty() || password.isEmpty() -> {
                                Toast.makeText(this, R.string.error_field_empty, Toast.LENGTH_SHORT).show()
                            }
                            password.length < 8 -> {
                                Toast.makeText(this, R.string.error_minimum_password, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                AnimationUtil.playLoginAnimation(binding)
            }
        }
    }

    private fun checkLogin(pUsername: String, pPassword: String) {
        val loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]
        loginViewModel.checkLogin(pUsername, pPassword)
    }

    private fun getUserInfo(sharedPref: SharedPreferences) {
        val loginViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[LoginViewModel::class.java]
        loginViewModel.userInfo.observe(this) {
            val fulltoken = "Bearer ${it.token}"
            USER_TOKEN = fulltoken

            val editor = sharedPref.edit()
            editor.putString(getString(R.string.user_token), fulltoken)
            editor.apply()
        }
    }

    private fun moveToHome() {
        val homeIntent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(homeIntent)
    }

    private fun buttonOn(state: Boolean) {
        binding.btnLogin.isEnabled = state
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarLogin.visibility = if (state) View.VISIBLE else View.GONE
    }
}