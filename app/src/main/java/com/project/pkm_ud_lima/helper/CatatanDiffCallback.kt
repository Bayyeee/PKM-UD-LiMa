package com.project.pkm_ud_lima.helper

import androidx.recyclerview.widget.DiffUtil
import com.project.pkm_ud_lima.database.Catatan


// helper untuk melakukan pengecekan apakah ada perubahan pada data & list catatan. kelas ini akan dipanggil di kelas CatatanAdapter
class CatatanDiffCallback(private val oldCatatanList: List<Catatan>, private val newCatatanList: List<Catatan>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldCatatanList.size
    }

    override fun getNewListSize(): Int {
        return newCatatanList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCatatanList[oldItemPosition].id == newCatatanList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCatatan = oldCatatanList[oldItemPosition]
        val newCatatan = newCatatanList[newItemPosition]
        return oldCatatan.judul == newCatatan.judul && oldCatatan.isi == newCatatan.isi
    }
}