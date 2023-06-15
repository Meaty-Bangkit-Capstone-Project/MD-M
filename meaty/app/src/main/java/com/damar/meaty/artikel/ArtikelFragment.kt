package com.damar.meaty.artikel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.local.Artikel
import com.damar.meaty.login.LoginActivity
import com.damar.meaty.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ArtikelFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_artikel, container, false)
        tabLayout = view.findViewById(R.id.tabs)
        viewPager = view.findViewById(R.id.view_pager)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.story_list)
//        setHasOptionsMenu(true)
        val sectionsPagerAdapter = SectionsPagerAdapter(requireActivity())
        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            // Set judul tab sesuai dengan posisi
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when (position) {
            0 -> getString(R.string.tab_all)
            1 -> getString(R.string.tab_recipe)
            2 -> getString(R.string.tab_tips)
            3 -> getString(R.string.tab_lifestyle)
            else -> ""
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_setting -> {
                val settingIntent = Intent(requireContext(), SettingActivity::class.java)
                startActivity(settingIntent)
                requireActivity().finish()
                return true
            }
            R.id.btn_logout -> {
                val sharedPref = requireContext().getSharedPreferences(getString(R.string.pref_name), Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString(getString(R.string.user_token), "")
                editor.remove(getString(R.string.upload_token))
                editor.apply()

                val logoutIntent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(logoutIntent)
                requireActivity().finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    inner class SectionsPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        override fun getItemCount(): Int {
            return 4
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> AllFragment()
                1 -> RecipeFragment()
                2 -> TipsFragment()
                3 -> GayaHidupFragment()
                else -> Fragment()
            }
        }
    }
}