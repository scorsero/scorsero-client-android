package io.github.dmi3coder.scorsero

import android.app.Application
import io.github.dmi3coder.scorsero.data.source.ScoreRepository

/**
 * Created by dim3coder on 2:19 PM 7/2/17.
 */
class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    ScoreRepository.init(this)
  }

}