package io.github.dmi3coder.scorsero.data.source.local

import android.util.Log
import com.github.debop.kodatimes.dateTimeUTC
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
    val toTime = date.dayOfYear().roundCeilingCopy().toDate().time
    val fromTime = date.dayOfYear().roundFloorCopy().toDate().time
    return dao.subscribeAllForDate(fromTime, toTime)
  }

  override fun insert(score: Score): Int {
    dao.insert(score)
    return 0 //TODO handle outcome data
  }

  override fun update(vararg scores: Score): Int {
    dao.update(*scores)
    return 0 //TODO handle outcome data
  }

  override fun delete(vararg scores: Score): Boolean {
    dao.delete(*scores)
    return true //TODO handle outcome data
  }
}