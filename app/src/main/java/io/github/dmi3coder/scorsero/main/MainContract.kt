package io.github.dmi3coder.scorsero.main

import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView
import io.github.dmi3coder.scorsero.data.Score
import io.reactivex.Flowable

/**
 * Created by dim3coder on 6:50 PM 7/2/17.
 */
interface MainContract {

  interface View : BaseView<Presenter> {
    fun showScores(scores: Flowable<List<Score>>)
    fun editScore(score: Score)
    fun setDate(date: String?)
  }

  interface Presenter : BasePresenter {
    fun restoreTitle(title: String?)
    fun editScore(score: Score)
    fun removeScore(score: Score)
    fun completeScore(score: Score)
  }
}