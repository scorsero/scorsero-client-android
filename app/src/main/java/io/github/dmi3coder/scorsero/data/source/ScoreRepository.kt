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
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.DeprecationLevel.WARNING
import kotlin.reflect.KFunction1

/**
 * Created by dim3coder on 9:55 AM 7/16/17.
 */

@Singleton
class ScoreRepository @Inject constructor(val local: ScoreDataSource) {


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

  fun subscribeScoresFor(interval: Interval): Flowable<List<Score>> {
    return Flowable.fromPublisher(local.subscribeScoresFor(interval))
  }

  fun subscribeElementCountFor(interval: Interval): Flowable<Int> {
    return this.subscribeElementCountFor(interval.start, interval.end)
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
    lateinit var scoreDatabase: ScoreDatabase

    @Deprecated(message = "Dagger introduced",
        replaceWith = ReplaceWith("@Inject ScoreRepository"),
        level = WARNING)
    @JvmStatic fun getInstance(): ScoreRepository {
      return INSTANCE
    }

    @Deprecated(message = "Dagger introduced, please remove any initialization", level = WARNING)
    @JvmStatic fun init(applicationContext: Context) {
      scoreDatabase = Room.databaseBuilder(applicationContext,
          ScoreDatabase::class.java, "score").build()
      INSTANCE = ScoreRepository(
          local = ScoreLocalDataSource(scoreDatabase.scoreDao()))
    }
  }
}