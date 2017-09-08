package io.github.dmi3coder.scorsero.main

import com.github.debop.kodatimes.days
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import org.joda.time.Interval
import javax.inject.Inject


/**
 * Created by dim3coder on 12:39 PM 7/3/17.
 */
class MainPresenter(var view: MainContract.View,
    val interval: Interval,
    var title: String? = null) : MainContract.Presenter {

  @Inject
  lateinit var repository: ScoreRepository

  override fun start() {
    subscribeScores(interval)
    var comparableInterval = Interval(interval.start,interval.end.minusMillis(1))
    if (title == null) {
      var toList = comparableInterval.days().toList()
      if (comparableInterval.containsNow() && toList.size == 1) {
        title = "Today"
      } else {
        title = comparableInterval.start.toString("dd MMM YYYY")
      }
    }
    view.setDate(title)
  }

  private fun subscribeScores(interval: Interval) {
    view.showScores(repository.subscribeScoresFor(interval))
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

  override fun restoreTitle(title: String?) {
    this.title = title
  }

  init {
    view.setPresenter(this)
  }

}