package com.project.pkm_ud_lima.ui.fragment

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.pkm_ud_lima.database.Catatan
import com.project.pkm_ud_lima.databinding.ItemCatatanBinding
import com.project.pkm_ud_lima.helper.CatatanDiffCallback
import com.project.pkm_ud_lima.ui.activity.CatatanAddUpdateActivity
import com.project.pkm_ud_lima.ui.fragment.CatatanAdapter.CatatanViewHolder
import java.util.*


// memanggil kelas CatatanDiffCallback untuk melakukan pengecekan perubahan data
class CatatanAdapter : RecyclerView.Adapter<CatatanViewHolder>() {
    private val listCatatan = ArrayList<Catatan>()
    fun setListCatatan(listCatatan: List<Catatan>) {
        val diffCallback = CatatanDiffCallback(this.listCatatan, listCatatan)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listCatatan.clear()
        this.listCatatan.addAll(listCatatan)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val binding = ItemCatatanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatatanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        holder.bind(listCatatan[position])
    }

    override fun getItemCount(): Int {
        return listCatatan.size
    }

    inner class CatatanViewHolder(private val binding: ItemCatatanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(catatan: Catatan) {
            with(binding) {
                tvItemTitle.text = catatan.judul
                tvItemDate.text = catatan.tanggal
                tvItemDescription.text = catatan.isi
                cvItemNote.setOnClickListener {
                    val intent = Intent(it.context, CatatanAddUpdateActivity::class.java)
                    intent.putExtra(CatatanAddUpdateActivity.EXTRA_NOTE, catatan)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}