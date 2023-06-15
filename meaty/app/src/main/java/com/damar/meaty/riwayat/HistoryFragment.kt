package com.damar.meaty.riwayat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.damar.meaty.R
import com.damar.meaty.api.ApiConfig
import com.damar.meaty.databinding.FragmentHistoryBinding
import com.damar.meaty.home.HomeFragment
import com.damar.meaty.response.HistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var progressBar: ProgressBar

    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HistoryAdapter(requireContext(), arrayListOf())

        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHistory.adapter = adapter

        progressBar = binding.progressBarHistory


        remoteGetHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun remoteGetHistory() {
        showLoading(true)

        val userId = HomeFragment.UserSession.userId // Ambil userId dari UserSession

        ApiConfig.getApiService().getHistory(userId).enqueue(object : Callback<ArrayList<HistoryResponse>> {
            override fun onResponse(
                call: Call<ArrayList<HistoryResponse>>,
                response: Response<ArrayList<HistoryResponse>>
            ) {
                showLoading(false) // Sembunyikan ProgressBar setelah data berhasil dimuat

                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        setDataToAdapter(data)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<HistoryResponse>>, t: Throwable) {
                showLoading(false) // Sembunyikan ProgressBar jika ada kegagalan

                Log.d("Error", "" + t.stackTraceToString())
            }
        })
    }

    private fun setDataToAdapter(data: ArrayList<HistoryResponse>) {
        val newData = ArrayList<HistoryResponse>()
        newData.addAll(data.reversed()) // Membalikkan urutan data agar yang terbaru ditampilkan di atas

        adapter.setData(newData)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarHistory.visibility = if (state) View.VISIBLE else View.GONE
    }
}
