package io.github.dmi3coder.scorsero.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import io.github.dmi3coder.scorsero.data.Score

/**
 * Created by dim3coder on 2:14 PM 7/2/17.
 */
@Database(entities = arrayOf(Score::class), version = 2)
abstract class ScoreDatabase : RoomDatabase() {
  public abstract fun scoreDao(): ScoreDao

}