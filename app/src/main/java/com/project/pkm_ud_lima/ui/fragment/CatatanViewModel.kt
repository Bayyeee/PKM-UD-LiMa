package com.project.pkm_ud_lima.ui.fragment

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.project.pkm_ud_lima.database.Catatan
import com.project.pkm_ud_lima.repository.CatatanRepository

class CatatanViewModel(application: Application) : ViewModel() {
    private val mCatatanRepository: CatatanRepository = CatatanRepository(application)

    fun getAllCatatan(): LiveData<List<Catatan>> {
        return mCatatanRepository.getAllCatatan()
    }
}