package io.github.dmi3coder.scorsero.score

import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView

/**
 * Created by dim3coder on 5:58 PM 7/4/17.
 */
interface ScoreCreationContract {

  interface View : BaseView<Presenter> {
    fun setVisibility(visible: Boolean = false)
    fun setScore(scoreData: Map<Int, Any>?)
    fun clear()
  }

  enum class ViewState {
    OPEN, HIDED, CLOSED
  }

  interface Presenter : BasePresenter {
    fun processScore(scoreData: Map<Int, Any>, state: ViewState)

  }
}