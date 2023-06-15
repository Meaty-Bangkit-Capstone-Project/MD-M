package com.damar.meaty.hasil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.meaty.R
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.databinding.ActivityHasilBinding
import com.damar.meaty.home.HomeFragment
import com.damar.meaty.response.HistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HasilActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: HasilAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHasilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = HasilAdapter(this, arrayListOf())

        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter

        progressBar = binding.progressBarHasil

        supportActionBar?.title = getString(R.string.hasil_terbaru)

        remoteGetHistory()

    }

    private fun remoteGetHistory() {
        showLoading(true)

        val userId = HomeFragment.UserSession.userId

        ApiConfig.getApiService().getHistory(userId).enqueue(object :
            Callback<ArrayList<HistoryResponse>> {
            override fun onResponse(
                call: Call<ArrayList<HistoryResponse>>,
                response: Response<ArrayList<HistoryResponse>>
            ) {
                showLoading(false)

                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        setDataToAdapter(data)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<HistoryResponse>>, t: Throwable) {
                showLoading(false)

                Log.d("Error", "" + t.stackTraceToString())
            }
        })
    }

    private fun setDataToAdapter(data: ArrayList<HistoryResponse>) {
        val newData = ArrayList<HistoryResponse>()
        newData.addAll(data.reversed())

        adapter.setData(newData)
    }

    private fun showLoading(state: Boolean) {
        progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
