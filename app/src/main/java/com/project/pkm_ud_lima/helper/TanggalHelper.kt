package com.project.pkm_ud_lima.helper

import java.util.Date
import java.util.Locale


// snippet kode untuk tanggal catatan agar kode lebih rapi
object TanggalHelper {
    fun getTanggalNow(): String {
        val dateFormat = java.text.SimpleDateFormat("EEEE d MMMM yyyy HH:mm:ss", Locale("in", "ID"))
        val date = Date()
        return dateFormat.format(date)
    }
}