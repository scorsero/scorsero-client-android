package io.github.dmi3coder.scorsero.score

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter

/**
 * Created by dim3coder on 6:49 PM 7/4/17.
 */
class ScoreCreationController(
    var bottomSheetBehavior: BottomSheetBehavior<View>) : ScoreCreationContract.View {


  internal var presenter: Presenter? = null

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun setVisibility(visible: Boolean) {
    bottomSheetBehavior.state =
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
}