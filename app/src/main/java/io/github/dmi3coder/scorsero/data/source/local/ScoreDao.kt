package io.github.dmi3coder.scorsero.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import io.github.dmi3coder.scorsero.data.Score


/**
 * Created by dim3coder on 1:02 PM 7/2/17.
 */
@Dao
interface ScoreDao {

  @Query("SELECT * FROM score")
  fun getAll(): List<Score>

  @Query("SELECT * FROM score WHERE creation_date BETWEEN :fromDate AND :toDate")
  fun getAllForDate(fromDate: Long, toDate: Long): Array<Score>

  @Insert(onConflict = OnConflictStrategy.FAIL)
  fun insert(score: Score)

  @Update
  fun update(vararg scores: Score)

  @Delete
  fun delete(vararg scores: Score)

}