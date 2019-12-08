package com.example.mobilearcanoid

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attempt(val date: String, val score: Int,val lat:Double,val lon:Double, @PrimaryKey(autoGenerate = true) val id: Int = 0)
