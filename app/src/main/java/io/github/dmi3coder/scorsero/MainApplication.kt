package io.github.dmi3coder.scorsero

import android.app.Application
import android.arch.persistence.room.Room
import io.github.dmi3coder.scorsero.data.source.local.ScoreDatabase

/**
 * Created by dim3coder on 2:19 PM 7/2/17.
 */
class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    scoreDatabase = Room.databaseBuilder(applicationContext,
        ScoreDatabase::class.java, "score").build()
  }

  companion object {
    var scoreDatabase: ScoreDatabase? = null
  }
}