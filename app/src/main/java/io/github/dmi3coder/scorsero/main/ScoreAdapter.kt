package io.github.dmi3coder.scorsero.main

import android.graphics.Paint
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
class ScoreAdapter(val presenter: Presenter) : RecyclerView.Adapter<ScoreViewHolder>() {
  private lateinit var items: List<Score>

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_score, parent, false)
    return ScoreViewHolder(itemView as ViewGroup)
  }

  override fun onBindViewHolder(holder: ScoreViewHolder?, position: Int) {
    holder!!.itemView.title.text = items[position].title ?: "Empty item"
    val itemView = holder.itemView
    if (items[position].completed ?: false) {
      itemView.title.paintFlags += Paint.STRIKE_THRU_TEXT_FLAG
    } else {
      itemView.title.paintFlags = 0
    }
    itemView.apply {
      description.text = items[position].description ?: "This score needs description"
      setOnClickListener {
        presenter.completeScore(items[position])
      }
      setOnLongClickListener {
        presenter.removeScore(items[position])
        true
      }
    }
  }

  override fun getItemCount(): Int = items.size

  fun setItems(scores: List<Score>) {
    this.items = scores
    notifyDataSetChanged()
  }
}

class ScoreViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView)