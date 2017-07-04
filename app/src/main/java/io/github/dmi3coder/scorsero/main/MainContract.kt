package io.github.dmi3coder.scorsero.main

import android.arch.lifecycle.LiveData
import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView
import io.github.dmi3coder.scorsero.data.Score

/**
 * Created by dim3coder on 6:50 PM 7/2/17.
 */
interface MainContract {

  interface View : BaseView<Presenter> {
    fun showScores(scores: LiveData<List<Score>>)
  }

  interface Presenter : BasePresenter {
    fun refreshScores()
    fun removeScore(score: Score)
  }
}