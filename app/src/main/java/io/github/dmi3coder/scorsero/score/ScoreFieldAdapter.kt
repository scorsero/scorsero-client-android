package io.github.dmi3coder.scorsero.score

import android.content.DialogInterface
import android.content.res.ColorStateList
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import kotlinx.android.synthetic.main.item_score_field.view.field_icon
import kotlinx.android.synthetic.main.item_score_field.view.field_title
import kotlinx.android.synthetic.main.item_score_field.view.field_value
import org.joda.time.DateTime
import org.joda.time.LocalDate


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
    var showTitle = true
    var value: String? = null
    var drawable: Int? = null
    val context = holder!!.itemView.context
    var okResponseHandler: ((DialogInterface, Int) -> Unit)? = null
    val builder = AlertDialog.Builder(context)
    var dialogView: View? = null

    holder.itemView.field_icon.backgroundTintList = ColorStateList.valueOf(
        context.getColor(R.color.colorPrimaryDark))
    //TODO: extract class on modularity
    when (position) {
      0 -> {
        title = "Title"
        value = score.title
        drawable = R.drawable.ic_chat
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.maxLines = 1
        dialogView = input
        okResponseHandler = { _, _ -> score.title = input.text.toString() }
      }
      1 -> {
        title = "Priority"
        value = "${score.priority}st priority"
        drawable = R.drawable.ic_notifications
        builder.setItems(
            ScoreStarterController.priorities.map { it.first.toString() }.toTypedArray()) { _, priorityPosition ->
          score.priority = priorityPosition
          this.notifyItemChanged(position)
        }
        if (score.priority != null) {
          holder.itemView.field_icon.backgroundTintList = ColorStateList.valueOf(
              context.getColor(ScoreStarterController.priorities[score.priority!!].second))
        }
      }
      2 -> {
        title = "Date"
        showTitle = false
        value = DateTime(score.creationDate).toString("dd MMM YYYY")
        drawable = R.drawable.ic_event
        val picker = DatePicker(context)
        dialogView = picker
        okResponseHandler = { _, _ ->
          score.creationDate = LocalDate(picker.year, picker.month.inc(),
              picker.dayOfMonth).toDate().time
        }

      }
      3 -> {
        title = "Description"
        value = score.description
        drawable = R.drawable.ic_chat
        val input = EditText(context)
        input.inputType = InputType.TYPE_CLASS_TEXT
        dialogView = input
        okResponseHandler = { _, _ -> score.description = input.text.toString() }
      }
    }
    builder.setView(dialogView)
    if (showTitle) builder.setTitle(title)
    builder.setPositiveButton(android.R.string.ok) { var1, var2 ->
      okResponseHandler?.invoke(var1, var2)
      this.notifyItemChanged(position)
    }

    builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
    val dialog = builder.create()

    holder.itemView.apply {
      this.field_title.text = title
      this.field_value.text = value
      this.field_icon.setImageResource(drawable!!)
      setOnClickListener { dialog.show() }
    }
  }

  override fun getItemCount(): Int = 4
}

class ScoreFieldHolder(itemView: View) : RecyclerView.ViewHolder(itemView)