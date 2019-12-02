package com.example.mobilearcanoid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.leaderboard.*

class LeaderboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)
        var score=intent.getIntExtra("score",0)
        scoreTextView.text=score.toString()

        backgroundLayout.setOnClickListener {
            var int = Intent(this, MainActivity::class.java)
            startActivity(int)
            finish()
        }
    }
}
