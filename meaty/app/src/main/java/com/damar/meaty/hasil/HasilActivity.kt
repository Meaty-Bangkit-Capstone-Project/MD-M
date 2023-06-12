package com.damar.meaty.hasil

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.meaty.MainActivity
import com.damar.meaty.R
import com.damar.meaty.adapter.LoadingStateAdapter
import com.damar.meaty.databinding.ActivityHasilBinding

class HasilActivity : AppCompatActivity() {

    private var _binding: ActivityHasilBinding? = null
    private val binding get() = _binding!!
    private val listViewModel: ListViewModel by viewModels {
        ViewModelFactory()
    }
    private val adapter = ListHasilAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = getString(R.string.story_list)

    }

    override fun onResume() {
        super.onResume()
        getStoryList()
    }

    private fun showRecycleView(){
        binding.rvStory.apply{
            val layoutManager = LinearLayoutManager(this@HasilActivity)
            binding.rvStory.layoutManager = layoutManager

            val itemDecoration = DividerItemDecoration(this@HasilActivity, layoutManager.orientation)
            binding.rvStory.addItemDecoration(itemDecoration)

            setHasFixedSize(true)
        }
    }

    private fun getStoryList(){
        showRecycleView()


        binding.rvStory.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            }
        )

        listViewModel.story.observe(this) {
            adapter.submitData(lifecycle, it)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("targetFragment", "addScanFragment")
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }

    companion object{
        var USER_TOKEN: String? = "USER_TOKEN"
    }
}