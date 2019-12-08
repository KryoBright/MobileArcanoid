package com.example.mobilearcanoid

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Attempt::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun attemptDao(): AttemptDAO
}
