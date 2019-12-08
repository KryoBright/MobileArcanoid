package com.example.mobilearcanoid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LeaderboardListAdapter(val attempts: List<Pair<String, String>>): RecyclerView.Adapter<LeaderboardListAdapter.AttemptViewHolder>() {



    class AttemptViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dateTextView: TextView = itemView.findViewById(R.id.textContentDate)
        val scoreTextView: TextView = itemView.findViewById(R.id.textContentScore)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttemptViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.leader_board_content, parent, false)
        return AttemptViewHolder(view)
    }

    override fun getItemCount(): Int {
        return attempts.size
    }

    override fun onBindViewHolder(holder: AttemptViewHolder, position: Int) {
        holder.dateTextView.text = attempts.get(position).first
        holder.scoreTextView.text = attempts.get(position).second
    }
}
