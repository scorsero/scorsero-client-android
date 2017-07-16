package io.github.dmi3coder.scorsero.data.source.local

import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreDataSource
import io.reactivex.Flowable
import org.joda.time.DateTime

/**
 * Created by dim3coder on 9:55 AM 7/16/17.
 */

class ScoreLocalDataSource(val dao: ScoreDao) : ScoreDataSource {

  override fun getAllScores(): List<Score> = dao.getAll()

  override fun getScoresFor(date: DateTime): List<Score>? {
    val fromTime = date.dayOfYear().roundCeilingCopy().toDate().time
    val toTime = date.dayOfYear().roundFloorCopy().toDate().time
    return dao.getAllForDate(fromTime, toTime)
  }

  override fun subscribeAllScores(): Flowable<List<Score>> = dao.subscribeAll()

  override fun subscribeScoresFor(date: DateTime): Flowable<List<Score>> {
    val fromTime = date.dayOfYear().roundCeilingCopy().toDate().time
    val toTime = date.dayOfYear().roundFloorCopy().toDate().time
    return dao.subscribeAllForDate(fromTime, toTime)
  }
}