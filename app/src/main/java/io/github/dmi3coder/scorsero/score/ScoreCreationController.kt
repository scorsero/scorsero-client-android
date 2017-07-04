package io.github.dmi3coder.scorsero.score

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter

/**
 * Created by dim3coder on 6:49 PM 7/4/17.
 */
class ScoreCreationController() : Controller(), ScoreCreationContract.View, OnClickListener {

  internal var presenter: Presenter? = null
  var bottomSheetBehavior : BottomSheetBehavior<View>? = null

  constructor(bottomSheetBehavior: BottomSheetBehavior<View>) : this() {
    this.bottomSheetBehavior = bottomSheetBehavior
  }


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val view = inflater.inflate(R.layout.controller_score_starter, container, false)

    return view
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun setVisibility(visible: Boolean) {
    bottomSheetBehavior?.state =
        if (visible) BottomSheetBehavior.STATE_HIDDEN else BottomSheetBehavior.STATE_EXPANDED
  }

  override fun setScore(scoreData: Map<Int, Any>?) {

  }


  fun mapScoreToState(scoreData: Map<Int, Any>) {
    TODO("not implemented")
  }

  override fun clear() {
    TODO("not implemented")
  }

  override fun onClick(v: View?) {
    bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
  }

}