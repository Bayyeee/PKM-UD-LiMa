package com.project.pkm_ud_lima.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.project.pkm_ud_lima.database.Catatan
import com.project.pkm_ud_lima.database.CatatanDao
import com.project.pkm_ud_lima.database.CatatanRoomDatabase
import java.util.concurrent.Executors


// repository gunanya untuk menghubungkan antara database dengan viewmodel
class CatatanRepository(application: Application) {
    private val mCatatanDao: CatatanDao
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        val db = CatatanRoomDatabase.getDatabase(application)
        mCatatanDao = db.catatanDao()
    }

    fun getAllCatatan() : LiveData<List<Catatan>> {
        return mCatatanDao.getAllCatatan()
    }

    fun insert(catatan: Catatan) {
        executorService.execute {
            mCatatanDao.insert(catatan)
        }
    }

    fun update(catatan: Catatan) {
        executorService.execute {
            mCatatanDao.update(catatan)
        }
    }

    fun delete(catatan: Catatan) {
        executorService.execute {
            mCatatanDao.delete(catatan)
        }
    }
}
