package com.example.mobilearcanoid

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(val context: Context) {

    private val db: AppDatabase = Room.databaseBuilder(context,
        AppDatabase::class.java, ATTEMPT_DATABASE).build()

    fun getAllAttempts(onGetAttempt: (users: List<Attempt>)-> Unit) {

        GlobalScope.launch(Dispatchers.Main) {

            val attempts = withContext(Dispatchers.IO) {db.attemptDao().getAllAttempts()}

            onGetAttempt(attempts)

        }
    }

    fun saveNewAttempt(newAttempt: Attempt, onSave: ()-> Unit) {

        GlobalScope.launch(Dispatchers.IO) {
            db.attemptDao().addAttempt(newAttempt)
            onSave()
        }
    }

    companion object {
        private val ATTEMPT_DATABASE = "attemptDatabase"
    }
}
