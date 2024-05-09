package com.project.pkm_ud_lima.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.pkm_ud_lima.ui.activity.CatatanAddUpdateActivity
import com.project.pkm_ud_lima.ui.activity.CatatanAddUpdateViewModel
import com.project.pkm_ud_lima.ui.fragment.CatatanViewModel

// double check ketika memanggil kelas viewmodel di dalam activity
class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(mApplication: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(mApplication)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatatanViewModel::class.java)) {
            return CatatanViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(CatatanAddUpdateViewModel::class.java)) {
            return CatatanAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}