package io.github.dmi3coder.scorsero.main

import com.github.debop.kodatimes.days
import com.github.debop.kodatimes.startOfDay
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import org.joda.time.DateTime
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
    var comparableInterval: Interval
    try {
      comparableInterval = Interval(interval.start, interval.end.minusMillis(1))
    } catch (e: IllegalArgumentException) {
      comparableInterval = interval
    }

    if (title == null) {
      val toList = comparableInterval.days().toList()
      val todayInterval = Interval(DateTime().startOfDay(),
          DateTime().plusDays(1).startOfDay().minusMillis(1))
      if ((interval.containsNow() || todayInterval.contains(interval)) && toList.size == 1) {
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
    repository.update(score)
  }

  override fun editScore(score: Score) {
    view.editScore(score)
  }

  override fun removeScore(score: Score) {
    repository.delete(score)
  }

  override fun restoreTitle(title: String?) {
    this.title = title
  }

  init {
    view.setPresenter(this)
  }

}