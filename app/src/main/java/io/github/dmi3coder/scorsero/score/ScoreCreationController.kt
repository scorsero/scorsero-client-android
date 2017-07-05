package io.github.dmi3coder.scorsero.score

import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState.CLOSED
import kotlinx.android.synthetic.main.controller_score_starter.view.title_field

/**
 * Created by dim3coder on 6:49 PM 7/4/17.
 */
class ScoreCreationController() : Controller(), ScoreCreationContract.View, OnClickListener {

  internal var presenter: Presenter? = null
  var bottomSheetBehavior: BottomSheetBehavior<View>? = null
  internal var view: View? = null
  var operationScore: Score? = null

  constructor(bottomSheetBehavior: BottomSheetBehavior<View>) : this() {
    this.bottomSheetBehavior = bottomSheetBehavior
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_score_starter, container, false)
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
    view!!.title_field.setText(scoreData?.title)
  }


  override fun clear() {
    activity?.runOnUiThread {
      view!!.title_field.text = null
    }
  }

  override fun onClick(v: View?) {
    if (bottomSheetBehavior!!.state != BottomSheetBehavior.STATE_HIDDEN) {
      operationScore!!.title = view?.title_field?.text.toString()
      Thread {
        presenter!!.processScore(operationScore, CLOSED)
      }.start()
      bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
  }

}