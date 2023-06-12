package com.damar.meaty.setting

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatDelegate
import com.damar.meaty.MainActivity
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.databinding.ActivitySettingBinding
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingActivity : AppCompatActivity() {
    private lateinit var switchTheme: SwitchMaterial
    private lateinit var sharedPreferences: SharedPreferences
    private var _binding: ActivitySettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.setting)

        val img1 = findViewById<LinearLayout>(R.id.field_setting_language)
        img1.setOnClickListener {
            changeLanguage()
        }

        val img3 = findViewById<LinearLayout>(R.id.field_setting_display)
        img3.setOnClickListener {
            changeDisplay()
        }

//        switchTheme = findViewById(R.id.switch_theme)
//        sharedPreferences = getSharedPreferences("ThemePreferences", MODE_PRIVATE)
//
//        switchTheme.isChecked = isDarkModeActive()
//        switchTheme.setOnCheckedChangeListener { _, isChecked ->
//            setDarkMode(isChecked)
//        }

        AnimationUtil.playSettingAnimation(binding)
    }

    private fun isDarkModeActive(): Boolean {
        return sharedPreferences.getBoolean("isDarkModeActive", isSystemDefaultTheme())
    }

    private fun setDarkMode(isDarkModeActive: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("isDarkModeActive", isDarkModeActive)
        editor.apply()

        if (isDarkModeActive) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun isSystemDefaultTheme(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun changeLanguage() {
        val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
        startActivity(intent)
    }

    fun changeDisplay() {
        val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(mainIntent)
        finish()
    }
}