package com.damar.meaty.hasil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.meaty.R
import com.damar.meaty.response.HistoryResponse

class HasilAdapter(
    private val context: Context,
    private val dataList: ArrayList<HistoryResponse>
): RecyclerView.Adapter<HasilAdapter.MyViewHolder>() {


    class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val eImage = view.findViewById<ImageView>(R.id.imageView)
        val tvTimestamp = view.findViewById<TextView>(R.id.timestampTextView)
        val tvPrediction = view.findViewById<TextView>(R.id.predictionTextView)
        val tvNotes = view.findViewById<TextView>(R.id.notesTextView)
        val cvMain = view.findViewById<CardView>(R.id.card_view_hasil)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_hasil, parent, false)

        return MyViewHolder(itemView)
    }

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val history = dataList[position]
//        Glide.with(holder.itemView)
//            .load(history.image)
//            .into(holder.eImage)
//
//        val waktuPengambilan = context.resources.getString(R.string.waktu_pengambilan)
//        val hasilPrediksi = context.resources.getString(R.string.hasil_prediksi)
//        val catatan = context.resources.getString(R.string.note)
//
//        holder.tvTimestamp.text = "$waktuPengambilan: ${dataList[position].timestamp}"
//        holder.tvPrediction.text = "$hasilPrediksi: ${dataList[position].prediction}"
//        holder.tvNotes.text = "$catatan: ${dataList[position].notes}"
//        holder.cvMain.setOnClickListener {
//            Toast.makeText(context, "" + dataList[position].prediction, Toast.LENGTH_SHORT).show()
//        }
//    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = dataList[position]
        Glide.with(holder.itemView)
            .load(history.image)
            .into(holder.eImage)

        val waktuPengambilan = context.resources.getString(R.string.waktu_pengambilan)
        val hasilPrediksi = context.resources.getString(R.string.hasil_prediksi)
        val catatan = context.resources.getString(R.string.note)

        val timestamp = if (dataList[position].timestamp.length > 10) {
            dataList[position].timestamp.substring(0, 10)
        } else {
            dataList[position].timestamp
        }
        holder.tvTimestamp.text = "$waktuPengambilan: $timestamp"
        holder.tvPrediction.text = "$hasilPrediksi: ${dataList[position].prediction}"
        holder.tvNotes.text = "$catatan: ${dataList[position].notes}"
        holder.cvMain.setOnClickListener {
            Toast.makeText(context, "" + dataList[position].prediction, Toast.LENGTH_SHORT).show()
        }
    }


    override fun getItemCount(): Int {
        return if (dataList.isNotEmpty()) {
            1
        } else {
            0
        }
    }


    fun setData(data: ArrayList<HistoryResponse>){
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}