package io.github.dmi3coder.scorsero.data.source

import android.arch.persistence.room.Room
import android.content.Context
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.local.ScoreDatabase
import io.github.dmi3coder.scorsero.data.source.local.ScoreLocalDataSource
import io.reactivex.Flowable
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.Interval
import kotlin.reflect.KFunction1

/**
 * Created by dim3coder on 9:55 AM 7/16/17.
 */


class ScoreRepository(val local: ScoreDataSource) {


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

  fun subscribeElementCountFor(interval: Interval): Flowable<Int>{
    return this.subscribeElementCountFor(interval.start,interval.end)
  }

  fun subscribeElementCountFor(fromDate: DateTime, toDate: DateTime): Flowable<Int> {
    return Flowable.fromPublisher(local.subscribeScoreCountFor(fromDate, toDate))
  }

  fun insert(score: Score) {
    inBackground(local::insert, score)
  }

  fun update(score: Score) {
    inBackground(local::update, score)
  }

  fun delete(score: Score) {
    inBackground(local::delete, score)
  }

  fun inBackground(
      func: KFunction1<@ParameterName(name = "scores") Array<out Score>, Any>,
      vararg score: Score) {
    Thread {
      func(score)
    }.start()
  }

  companion object {
    private lateinit var INSTANCE: ScoreRepository
    internal lateinit var scoreDatabase: ScoreDatabase

    @JvmStatic fun getInstance(): ScoreRepository {
      return INSTANCE
    }

    @JvmStatic fun init(applicationContext: Context) {
      scoreDatabase = Room.databaseBuilder(applicationContext,
          ScoreDatabase::class.java, "score").build()
      INSTANCE = ScoreRepository(
          local = ScoreLocalDataSource(scoreDatabase.scoreDao()))
    }
  }
}