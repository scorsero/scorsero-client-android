package io.github.dmi3coder.scorsero.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.github.dmi3coder.scorsero.data.Score
import io.reactivex.Flowable


/**
 * Created by dim3coder on 1:02 PM 7/2/17.
 */
@Dao
interface ScoreDao {

  @Query("SELECT * FROM score")
  fun subscribeAll(): Flowable<List<Score>>

  @Query("SELECT * FROM score")
  fun getAll(): List<Score>

  @Query("SELECT * FROM score WHERE $CREATION_DATE_BETWEEN_ARGS")
  fun getAllForDate(fromDate: Long, toDate: Long): List<Score>

  @Query("SELECT * FROM score WHERE $CREATION_DATE_BETWEEN_ARGS")
  fun subscribeAllForDate(fromDate: Long, toDate: Long): Flowable<List<Score>>

  @Query("SELECT count(*) FROM score WHERE $CREATION_DATE_BETWEEN_ARGS AND $NOT_COMPLETED")
  fun subscribeElementCount(fromDate: Long, toDate: Long): Flowable<Int>

  @Insert(onConflict = OnConflictStrategy.FAIL)
  fun insert(vararg score: Score)

  @Update
  fun update(vararg scores: Score)

  @Delete
  fun delete(vararg scores: Score)

  companion object {
    const val CREATION_DATE_BETWEEN_ARGS = "creation_date >= :arg0 AND creation_date < :arg1"
    const val NOT_COMPLETED = "(completed = 0 OR completed IS NULL)"
  }

}