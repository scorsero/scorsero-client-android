package io.github.dmi3coder.scorsero.score

import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState
import java.util.Date

/**
 * Created by dim3coder on 11:59 PM 7/4/17.
 */
class ScoreCreationPresenter(
    var view: ScoreCreationContract.View,
    var repository: ScoreRepository = ScoreRepository.getInstance())
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
    if (scoreData!!.creationDate == null) scoreData.creationDate = Date().time
    if (scoreData.id == null) {
      repository.insert(scoreData)
    } else {
      repository.update(scoreData)
    }
    view.clear()
  }
}