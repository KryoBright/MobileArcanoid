package com.example.mobilearcanoid

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.leaderboard.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class LeaderboardActivity : AppCompatActivity(),AttemptListView {

    override fun setupScreenForAttemptList(attempts: List<Pair<String, String>>) {

        val adapter = LeaderboardListAdapter(attempts)
        leaderboardList.adapter = adapter
    }

    lateinit var presenter:LeaderboardListPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.leaderboard)
        var score=intent.getIntExtra("score",0)
        scoreTextView.text=score.toString()
        presenter = LeaderboardListPresenter(this, applicationContext)

        leaderboardList.layoutManager = presenter.getLayourManager()

        backgroundLayout.setOnClickListener {
            var int = Intent(this, MapsActivity::class.java)
            int.putExtra("score",score)
            startActivity(int)
            finish()
        }
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
            return
        }


    }

    override fun onResume() {
        super.onResume()
        presenter.onActivityResume()
    }

    override fun onDestroy() {
        presenter.onDestroyActivity()
        super.onDestroy()
    }

}
