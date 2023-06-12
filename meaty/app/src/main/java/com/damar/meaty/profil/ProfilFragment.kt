package com.damar.meaty.profil

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.TextView
import com.damar.meaty.R

class ProfilFragment : Fragment() {
    private lateinit var profilViewModel: ProfilViewModel
    private lateinit var myNameTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.profil)
//        setHasOptionsMenu(true)

        val editButton = view.findViewById<Button>(R.id.btn_edit)
        editButton.setOnClickListener {
            val intent = Intent(activity, EditProfilActivity::class.java)
            startActivity(intent)
        }
    }
}