package com.damar.meaty.riwayat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.meaty.R
import com.damar.meaty.adapter.ListStoryAdapter
import com.damar.meaty.adapter.LoadingStateAdapter
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.databinding.FragmentRiwayatBinding
import com.damar.meaty.detail.DetailStoryActivity
import com.damar.meaty.hasil.ListViewModel
import com.damar.meaty.hasil.ViewModelFactory
import com.damar.meaty.login.LoginActivity
import com.damar.meaty.setting.SettingActivity

class RiwayatFragment : Fragment() {

    private var _binding: FragmentRiwayatBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: ListViewModel by viewModels {
        ViewModelFactory()
    }

    private val adapter = ListStoryAdapter { story ->
        val detailIntent = Intent(requireContext(), DetailStoryActivity::class.java).apply {
            putExtra(DetailStoryActivity.NAMA, story.name)
            putExtra(DetailStoryActivity.DESKRIPSI, story.description)
            putExtra(DetailStoryActivity.GAMBAR, story.photoUrl)
            putExtra(DetailStoryActivity.CREATED_AT, story.createdAt)
        }
        startActivity(detailIntent)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRiwayatBinding.inflate(inflater, container, false)
        AnimationUtil.playRiwayatAnimation(binding)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.story_list)
//        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        showRecyclerView()
        getListStory()
    }

    private fun showRecyclerView() {
        binding.rvStory.apply {
            val layoutManager = LinearLayoutManager(requireContext())
            binding.rvStory.layoutManager = layoutManager

            val itemDecoration =
                DividerItemDecoration(requireContext(), layoutManager.orientation)
            binding.rvStory.addItemDecoration(itemDecoration)

            setHasFixedSize(true)
            adapter = this@RiwayatFragment.adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    this@RiwayatFragment.adapter.retry()
                }
            )
        }
    }

    private fun getListStory() {
        homeViewModel.story.observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
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
                val sharedPref = requireContext().getSharedPreferences(
                    getString(R.string.pref_name),
                    Context.MODE_PRIVATE
                )
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        var USER_TOKEN: String? = "USER_TOKEN"
    }
}