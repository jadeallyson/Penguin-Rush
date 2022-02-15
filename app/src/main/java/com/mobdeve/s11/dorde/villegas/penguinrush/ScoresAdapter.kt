package com.mobdeve.s11.dorde.villegas.penguinrush

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.dorde.villegas.penguinrush.databinding.ScoreRowBinding
import com.mobdeve.s11.dorde.villegas.penguinrush.model.Score

class ScoresAdapter (private val context: Context,
                     private val scoreList: ArrayList<Score?>?)
    : RecyclerView.Adapter<ScoresAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ScoreRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(itemBinding)
    }


    override fun getItemCount() = scoreList!!.size

    override fun onBindViewHolder(holder: ScoresAdapter.ViewHolder,
                                  position: Int) {
        holder.bindScore(scoreList!![position]!!)

    }

    fun addScores(scoreArrayList: ArrayList<Score?>?){
        scoreList!!.clear()
        scoreArrayList!!.addAll(scoreArrayList!!)
        notifyDataSetChanged()
    }

    fun addScore(score: Score?){
        scoreList!!.add(0, score)
        notifyItemInserted(0)
        notifyDataSetChanged()
    }

    fun removeScore(position: Int){
        scoreList!!.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val scoreBinding: ScoreRowBinding)
        :RecyclerView.ViewHolder(scoreBinding.root){

        fun bindScore(score: Score){
            scoreBinding.tvUsername.text = score.username
            scoreBinding.tvScore.text = score.score.toString()
        }
    }

}