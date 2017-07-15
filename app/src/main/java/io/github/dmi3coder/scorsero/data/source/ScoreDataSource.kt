package io.github.dmi3coder.scorsero.data.source

import android.arch.lifecycle.LiveData
import io.github.dmi3coder.scorsero.data.Score
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.MonthDay

/**
 * Created by dim3coder on 1:13 PM 7/15/17.
 */

interface ScoreDataSource {

  fun getAllScores(): List<Score>

  fun getScoreBy(id: Int) : Score?

  fun getScoresFor(date: DateTime): List<Score>?

  fun getScoresFor(month: MonthDay): List<Score>?

  fun getScoresIn(interval: Interval): List<Score>?


  //Live data support

  fun subscribeAllScores(): LiveData<List<Score>>

  fun subscribeScoreBy(id: Int): LiveData<Score>

  fun subscribeScoresFor(date: DateTime): LiveData<List<Score>>

  fun subscribeScoresIn(interval: Interval): List<Score>?

}