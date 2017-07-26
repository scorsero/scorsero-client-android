package io.github.dmi3coder.scorsero.main

import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import org.joda.time.DateTime


/**
 * Created by dim3coder on 12:39 PM 7/3/17.
 */
class MainPresenter(var view: MainContract.View) : MainContract.Presenter {

  lateinit var repository: ScoreRepository

  override fun start() {
    repository = ScoreRepository.getInstance()
    refreshScores()
  }

  override fun refreshScores() {
    view.showScores(repository.subscribeAllScores())
  }

  override fun completeScore(score: Score) {
    score.completed = !(score.completed ?: false)
    if (score.completed!!) {
      score.completionDate = System.currentTimeMillis()
    }
    Thread {
      repository.update(score)
    }.start()
  }

  override fun editScore(score: Score) {
    view.editScore(score)
  }

  override fun removeScore(score: Score) {
    Thread {
      repository.delete(score)
    }.start()
  }

  init {
    view.setPresenter(this)
  }

}