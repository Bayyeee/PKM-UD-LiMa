package com.project.pkm_ud_lima.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


// Inisialiasi entity catatan ke database
@Entity
@Parcelize
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "judul")
    var judul: String? = null,

    @ColumnInfo(name = "isi")
    var isi: String? = null,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null
) : Parcelable