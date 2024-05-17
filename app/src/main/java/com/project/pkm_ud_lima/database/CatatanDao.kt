package com.project.pkm_ud_lima.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


// Interface untuk menghandle operasi database
@Dao
interface CatatanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(catatan: Catatan)

    @Update
    fun update(catatan: Catatan)

    @Delete
    fun delete(catatan: Catatan)

    @Query("SELECT * FROM catatan ORDER BY id DESC")
    fun getAllCatatan(): LiveData<List<Catatan>>

}