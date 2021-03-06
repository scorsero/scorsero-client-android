package io.github.dmi3coder.scorsero.score

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState.CLOSED
import io.github.dmi3coder.scorsero.utils.hideKeyboard
import kotlinx.android.synthetic.main.controller_score_starter.view.priority_holder
import kotlinx.android.synthetic.main.controller_score_starter.view.title_field


/**
 * Created by dim3coder on 6:49 PM 7/4/17.
 */
class ScoreStarterController() : Controller(), ScoreCreationContract.View, OnClickListener {

  internal var presenter: Presenter? = null
  var bottomSheetBehavior: BottomSheetBehavior<View>? = null
  internal var view: View? = null
  var operationScore: Score? = null


  constructor(bottomSheetBehavior: BottomSheetBehavior<View>) : this() {
    this.bottomSheetBehavior = bottomSheetBehavior
    bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, slideOffset: Float) {
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
          bottomSheet.title_field.clearFocus()
          bottomSheet.hideKeyboard()
        }
      }
    })
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_score_starter, container, false)
    fillPriorityHolder(view!!.priority_holder)
    return view!!
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun setVisibility(visible: Boolean) {
    bottomSheetBehavior?.state =
        if (visible) BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
  }

  override fun setScore(scoreData: Score?) {
    operationScore = scoreData
    scoreToState(scoreData)
  }


  fun scoreToState(scoreData: Score?) {
    activity!!.runOnUiThread {
      view!!.title_field.setText(scoreData?.title)
    }
  }

  fun fillPriorityHolder(view: LinearLayout) {
    priorities.forEach {
      view.addView(
          View(activity).apply {
            val drawable = activity!!.getDrawable(R.drawable.shape_priority_circle)
            this.background = drawable
            this.setColors(activity!!.getColor(it.second), 0)
            this.setOnClickListener { _ ->
              for (i in 0.rangeTo(view.childCount - 1)) {
                view.getChildAt(i).setColors(
                    activity!!.getColor(ScoreStarterController.priorities[i].second), 0)
              }
              this.setColors(activity!!.getColor(it.second), Color.YELLOW)
              operationScore?.priority = it.first
            }
          },
          LinearLayout.LayoutParams(42.px, 42.px).apply { setMargins(8.px, 8.px, 8.px, 8.px) })
    }
  }


  override fun clear() {
    activity?.runOnUiThread {
      view!!.title_field.text = null
      val priority_holder = view!!.priority_holder
      for(itemPosition in 0.rangeTo(priority_holder.childCount - 1)){
        priority_holder.getChildAt(itemPosition).setStrokeColor(0)
      }
    }
  }

  override fun onClick(v: View) {
    if (bottomSheetBehavior!!.state != BottomSheetBehavior.STATE_HIDDEN) {
      if (!validateFields()) return

      operationScore!!.title = view?.title_field?.text.toString()
      Thread {
        presenter!!.processScore(operationScore, CLOSED)
      }.start()
      bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
  }

  fun validateFields(): Boolean {
    if (view!!.title_field.text.trim().isEmpty()) {
      Toast.makeText(activity, "Field must not be empty", Toast.LENGTH_SHORT).show()
      view!!.requestFocus()
      val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.showSoftInput(view!!.title_field, 0)
      return false
    }
    return true
  }

  companion object {
    //TODO: come up with priorities colors
    val priorities: Array<Pair<Int, Int>> = arrayOf(
        Pair(0, android.R.color.holo_red_dark),
        Pair(1, android.R.color.holo_green_dark),
        Pair(2, android.R.color.holo_blue_light),
        Pair(3, android.R.color.holo_blue_bright),
        Pair(4, android.R.color.holo_blue_dark),
        Pair(5, android.R.color.holo_orange_light)
    )
  }
}

val Int.dp: Int
  get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
  get() = (this * Resources.getSystem().displayMetrics.density).toInt()


fun View.setStrokeColor(strokeColor: Int){
  (background as GradientDrawable).setStroke(2, strokeColor)
}
fun View.setColors(fillColor: Int, strokeColor: Int) {
  (background as GradientDrawable).setStroke(2, strokeColor)
  (background as GradientDrawable).setColor(fillColor)
}