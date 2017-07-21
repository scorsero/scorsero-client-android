package io.github.dmi3coder.scorsero.data.source

import io.github.dmi3coder.scorsero.data.Score
import io.reactivex.Flowable
import org.joda.time.DateTime

/**
 * Created by dim3coder on 1:13 PM 7/15/17.
 */

interface ScoreDataSource {

  fun getAllScores(): List<Score>

  fun getScoresFor(date: DateTime): List<Score>?

  //Live data support

  fun subscribeAllScores(): Flowable<List<Score>>

  fun subscribeScoresFor(date: DateTime): Flowable<List<Score>>

  fun insert(vararg score: Score): Int

  fun update(vararg scores: Score): Int

  fun delete(vararg scores: Score): Boolean

}