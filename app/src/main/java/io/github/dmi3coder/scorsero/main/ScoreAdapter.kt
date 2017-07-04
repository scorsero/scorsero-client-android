package io.github.dmi3coder.scorsero.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import kotlinx.android.synthetic.main.item_score.view.description
import kotlinx.android.synthetic.main.item_score.view.title

/**
 * Created by dim3coder on 12:43 PM 7/3/17.
 */
class ScoreAdapter(val presenter: Presenter,
    val items: List<Score>) : RecyclerView.Adapter<ScoreViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_score, parent, false)
    return ScoreViewHolder(itemView as ViewGroup)
  }

  override fun onBindViewHolder(holder: ScoreViewHolder?, position: Int) {
    holder!!.itemView.title.text = items[position].title ?: "Empty item"
    holder.itemView.description.text = items[position].description ?: "This score needs description"
    holder.itemView.setOnLongClickListener {
      presenter.removeScore(items[position])
      true
    }
  }

  override fun getItemCount(): Int = items.size

}

class ScoreViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView)