package com.example.tsdc

import android.app.Application
import com.example.tsdc.data.local.VinylRoomDatabase

class VinylsApplication : Application() {
    val database: VinylRoomDatabase by lazy {
        VinylRoomDatabase.getDatabase(this)
    }
}