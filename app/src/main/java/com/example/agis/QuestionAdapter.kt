package com.example.agis

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuestionAdapter(
    private val qaPairs: List<Pair<String, String>>,
    private val onQuestionClick: (String) -> Unit
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = qaPairs[position].first
        holder.questionText.text = question
        holder.itemView.setOnClickListener {
            onQuestionClick(qaPairs[position].second) // Send answer on question click
        }
    }

    override fun getItemCount() = qaPairs.size

    class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val questionText: TextView = itemView.findViewById(R.id.question_text)
    }
}