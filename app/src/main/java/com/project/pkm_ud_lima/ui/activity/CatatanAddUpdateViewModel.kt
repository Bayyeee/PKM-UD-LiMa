package com.project.pkm_ud_lima.ui.activity

import android.app.Application
import androidx.lifecycle.ViewModel
import com.project.pkm_ud_lima.database.Catatan
import com.project.pkm_ud_lima.repository.CatatanRepository


// menghubungkan repository database dengan aplikasi/tampilan ui
class CatatanAddUpdateViewModel(application: Application) : ViewModel() {
    private val mCatatanRepository: CatatanRepository = CatatanRepository(application)

    fun insert(catatan: Catatan) {
        mCatatanRepository.insert(catatan)
    }

    fun update(catatan: Catatan) {
        mCatatanRepository.update(catatan)
    }

    fun delete(catatan: Catatan) {
        mCatatanRepository.delete(catatan)
    }
}