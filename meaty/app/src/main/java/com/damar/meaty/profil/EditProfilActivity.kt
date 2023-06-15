package com.damar.meaty.profil

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.navigation.findNavController
import com.damar.meaty.MainActivity
import com.damar.meaty.R
import com.damar.meaty.addscan.AddScanFragment
import com.damar.meaty.databinding.ActivityEditProfilBinding
import com.damar.meaty.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class EditProfilActivity : AppCompatActivity() {

    private var _binding: ActivityEditProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val genderOptions = arrayOf(getString(R.string.gender_male), getString(R.string.gender_female))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGender.adapter = adapter

        val btnSave: Button = findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            showSnackbar()
        }

        supportActionBar?.title = getString(R.string.setting_profil)
    }

    private fun showSnackbar() {
        val rootView: View = findViewById(android.R.id.content)
        Snackbar.make(rootView, getString(R.string.success_save), Snackbar.LENGTH_SHORT).show()
    }
}
