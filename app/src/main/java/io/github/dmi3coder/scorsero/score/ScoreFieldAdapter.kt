package io.github.dmi3coder.scorsero.score

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import kotlinx.android.synthetic.main.item_score_field.view.field_icon
import kotlinx.android.synthetic.main.item_score_field.view.field_title
import kotlinx.android.synthetic.main.item_score_field.view.field_value
import org.joda.time.DateTime

/**
 * Created by dim3coder on 8:23 AM 7/19/17.
 */
class ScoreFieldAdapter(val score: Score) : RecyclerView.Adapter<ScoreFieldHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ScoreFieldHolder {
    val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_score_field, parent,
        false)
    return ScoreFieldHolder(view)
  }

  /**
   * Binds view to [score] fields, in a future this structure will be converted to map for
   * modularity improvements (e.g extensible values)
   */
  override fun onBindViewHolder(holder: ScoreFieldHolder?, position: Int) {
    var title: String? = null
    var value: String? = null
    var drawable: Int? = null

    when (position) {
      0 -> {
        title = "Title"
        value = score.title
        drawable = R.drawable.ic_chat
      }
      1 -> {
        title = "Priority"
        value = "${score.priority}st priority"
        drawable = R.drawable.ic_notifications
      }
      2 -> {
        title = "Date"
        value = DateTime(score.creationDate).toString("dd MMM YYYY")
        drawable = R.drawable.ic_event
      }
      3 -> {
        title = "Description"
        value = score.description
        drawable = R.drawable.ic_chat
      }
    }
    holder!!.itemView.apply {
      this.field_title.text = title
      this.field_value.text = value
      this.field_icon.setImageResource(drawable!!)
    }
  }

  override fun getItemCount(): Int = 4
}

class ScoreFieldHolder(itemView: View) : RecyclerView.ViewHolder(itemView)