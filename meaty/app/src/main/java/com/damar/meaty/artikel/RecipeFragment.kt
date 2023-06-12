package com.damar.meaty.artikel

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.damar.meaty.R
import com.damar.meaty.customview.AnimationUtil
import com.damar.meaty.databinding.FragmentAllBinding
import com.damar.meaty.databinding.FragmentRecipeBinding
import com.damar.meaty.local.Artikel

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private lateinit var binding: FragmentRecipeBinding
    private lateinit var beritaRecyclerView: RecyclerView
    private lateinit var artikelList: ArrayList<Artikel>
    private lateinit var beritaAdapter: BeritaAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRecipeBinding.inflate(inflater, container, false)
        val view = binding.root

        beritaRecyclerView = binding.bRecyclerView
        artikelList = createSampleArtikelList()
        beritaAdapter = BeritaAdapter(artikelList)

        binding.bRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.bRecyclerView.adapter = beritaAdapter

        AnimationUtil.playArtikelRecipeAnimation(binding)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = getString(R.string.story_list)
        setHasOptionsMenu(true)
    }

    private fun createSampleArtikelList(): ArrayList<Artikel> {
        val artikelList = ArrayList<Artikel>()
        val judulArray = resources.getStringArray(R.array.artikel_judul)
        val kategoriArray = resources.getStringArray(R.array.artikel_kategori)
        val gambarArray = resources.getStringArray(R.array.artikel_photo)
        val urlArray = resources.getStringArray(R.array.artikel_url)

        // val kategoriResepMakanan = getString(R.string.tab_recipe)

        for (i in judulArray.indices) {
            if (kategoriArray[i] == "Resep Olahan Daging Sapi") {
                val artikel = Artikel(judulArray[i], kategoriArray[i], gambarArray[i], urlArray[i])
                artikelList.add(artikel)
            }
        }
        return artikelList
    }


    inner class BeritaAdapter(private val artikelList: ArrayList<Artikel>) :
        RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder>() {

        inner class BeritaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val judulTextView: TextView = itemView.findViewById(R.id.tv_judul)
            val kategoriTextView: TextView = itemView.findViewById(R.id.tv_kategori)
            val gambarImageView: ImageView = itemView.findViewById(R.id.img_item_photo)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeritaViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_artikel, parent, false)
            return BeritaViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BeritaViewHolder, position: Int) {
            val berita = artikelList[position]
            holder.judulTextView.text = berita.judul
            holder.kategoriTextView.text = berita.kategori

            Glide.with(holder.itemView)
                .load(berita.gambarUrl)
                .into(holder.gambarImageView)

            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(berita.url))
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return artikelList.size
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