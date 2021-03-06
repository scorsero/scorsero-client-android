package io.github.dmi3coder.scorsero.main

import android.graphics.Color
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreStarterController
import kotlinx.android.synthetic.main.item_score.view.*

/**
 * Created by dim3coder on 12:43 PM 7/3/17.
 */
class ScoreAdapter(val presenter: Presenter) : RecyclerView.Adapter<ScoreViewHolder>() {
  private var items: List<Score>? = null

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreViewHolder {
    val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.item_score, parent, false)
    return ScoreViewHolder(itemView as ViewGroup)
  }

  override fun onBindViewHolder(holder: ScoreViewHolder?, position: Int) {
    val score = items!![position]
    holder!!.itemView.title.text = score.title ?: "Empty item"
    val itemView = holder.itemView
    itemView.completion_checkbox.isChecked = score.completed?:false
    val onTaskCompletedListener: (View) -> Unit = {
      presenter.completeScore(score)
    }
    arrayOf(itemView.title, itemView.description).forEach {
      if (score.completed ?: false) {
        it.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
      } else {
        it.paintFlags = 0
      }
    }
    itemView.apply {
      description.text = score.description ?: "This score needs description"
      title.setTextColor(Color.WHITE)
      score.priority?.apply {
        val priorityColor = ScoreStarterController.priorities.find { it.first == this }!!.second
        title.setTextColor(context.getColor(priorityColor))
      }
      setOnClickListener(onTaskCompletedListener)
      completion_checkbox.setOnClickListener(onTaskCompletedListener)
      setOnLongClickListener {
        presenter.removeScore(score)
        true
      }
    }
  }

  override fun getItemCount(): Int = items?.size ?: 0

  fun setItems(scores: List<Score>) {
    this.items = scores
    notifyDataSetChanged()
  }
}

class ScoreViewHolder(itemView: ViewGroup) : RecyclerView.ViewHolder(itemView)