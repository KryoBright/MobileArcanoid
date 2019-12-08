package com.example.mobilearcanoid

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AttemptDAO {

    @Query("SELECT * FROM attempt")
    fun getAllAttempts(): List<Attempt>

    @Insert
    fun addAttempt(attempt: Attempt)

}