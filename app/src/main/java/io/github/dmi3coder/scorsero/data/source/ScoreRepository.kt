package io.github.dmi3coder.scorsero.data.source

import io.github.dmi3coder.scorsero.MainApplication
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.local.ScoreLocalDataSource
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime

/**
 * Created by dim3coder on 9:55 AM 7/16/17.
 */


class ScoreRepository() {
  private val local: ScoreDataSource

  fun getAllScores(): Single<List<Score>> {
    return Single.just(local.getAllScores())
  }

  fun getScoresFor(date: DateTime): Single<List<Score>> {
    return Single.just(local.getScoresFor(date))
  }

  fun subscribeAllScores(): Flowable<List<Score>> {
    return Flowable.fromPublisher(local.subscribeAllScores())
  }

  fun subscribeScoresFor(date: DateTime): Flowable<List<Score>> {
    return Flowable.fromPublisher(local.subscribeScoresFor(date))
  }

  init {
    local = ScoreLocalDataSource(MainApplication.scoreDatabase!!.scoreDao())
  }

}