package com.example.mobilearcanoid

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class LeaderboardListPresenter(var view: AttemptListView?, val context: Context) {

    val repository = Repository(context)

    fun getLayourManager(): LinearLayoutManager {
        return LinearLayoutManager(view as Context)
    }

    fun onActivityResume() {

        repository.getAllAttempts {

            val data = it.sortedBy { -it.score }.map {
                Pair<String, String>(it.date, it.score.toString())
            }

            view?.setupScreenForAttemptList(data)
        }
    }

    fun onDestroyActivity() {
        view = null
    }
}
