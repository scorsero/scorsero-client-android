package io.github.dmi3coder.scorsero.score

import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView
import io.github.dmi3coder.scorsero.data.Score

/**
 * Created by dim3coder on 5:58 PM 7/4/17.
 */
interface ScoreCreationContract {

  interface View : BaseView<Presenter> {
    fun setVisibility(visible: Boolean = false)
    fun setScore(scoreData: Score?)
    fun clear()
  }

  enum class ViewState {
    OPEN, HIDED, CLOSED
  }

  interface Presenter : BasePresenter {
    fun start(score: Score)
    fun processScore(scoreData: Score?, state: ViewState)

  }
}