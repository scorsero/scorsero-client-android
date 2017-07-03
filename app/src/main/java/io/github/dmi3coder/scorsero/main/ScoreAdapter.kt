package io.github.dmi3coder.scorsero.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score

/**
 * Created by dim3coder on 12:43 PM 7/3/17.
 */
class ScoreAdapter(val items: List<Score>) : RecyclerView.Adapter<ScoreViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_score, parent, false)
    return ScoreViewHolder(itemView as ViewGroup)
  }

  override fun onBindViewHolder(holder: ScoreViewHolder?, position: Int) {

  }

  override fun getItemCount(): Int = 20

}

class ScoreViewHolder : RecyclerView.ViewHolder {

  constructor(itemView: ViewGroup) : super(itemView)

}