package com.project.pkm_ud_lima.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Inisialiasi database room untuk dihubungkan ke aplikasi
@Database(entities = [Catatan::class], version = 1)
abstract class CatatanRoomDatabase : RoomDatabase() {
    abstract fun catatanDao(): CatatanDao

    companion object {
        @Volatile
        private var INSTANCE: CatatanRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CatatanRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CatatanRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CatatanRoomDatabase::class.java,
                        "catatan_database"
                    )
                        .build()
                }
            }
            return INSTANCE as CatatanRoomDatabase
        }
    }
}