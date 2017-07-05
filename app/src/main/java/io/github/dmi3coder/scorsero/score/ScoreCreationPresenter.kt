package io.github.dmi3coder.scorsero.score

import io.github.dmi3coder.scorsero.MainApplication
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.local.ScoreDao
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState

/**
 * Created by dim3coder on 11:59 PM 7/4/17.
 */
class ScoreCreationPresenter(
    var view: ScoreCreationContract.View,
    var scoreDao: ScoreDao = MainApplication.scoreDatabase!!.scoreDao())
  : ScoreCreationContract.Presenter {

  var operationScore: Score? = null

  override fun start() {
    this.start(Score())
  }

  override fun start(score: Score) {
    operationScore = score
    view.setPresenter(this)
    view.setScore(operationScore)
  }

  override fun processScore(scoreData: Score?, state: ViewState) {
    scoreDao.insert(scoreData!!)
    view.clear()
  }
}