package io.github.dmi3coder.scorsero.main

import io.github.dmi3coder.scorsero.MainApplication
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.local.ScoreDao


/**
 * Created by dim3coder on 12:39 PM 7/3/17.
 */
class MainPresenter(var view: MainContract.View) : MainContract.Presenter {

  var scoreDao: ScoreDao? = null

  override fun start() {
    view.setPresenter(this)
    scoreDao = MainApplication.scoreDatabase!!.scoreDao()
    refreshScores()
  }

  override fun refreshScores() {
    view.showScores(scoreDao!!.getAll())
  }

  override fun removeScore(score: Score) {
    scoreDao?.delete(score)
  }

}