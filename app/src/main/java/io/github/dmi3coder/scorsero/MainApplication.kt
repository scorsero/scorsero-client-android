package io.github.dmi3coder.scorsero

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import dagger.Module
import dagger.Provides
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import javax.inject.Singleton

/**
 * Created by dim3coder on 2:19 PM 7/2/17.
 */
class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()
    ScoreRepository.init(this)
    Stetho.initializeWithDefaults(this)
    mainComponent = DaggerMainComponent.builder()
        .mainApplicationModule(MainApplicationModule(this))
        .build()
  }


  companion object {
    lateinit var mainComponent: MainComponent
  }

}

@Module
class MainApplicationModule(val application: Application) {

  @Provides
  @Singleton
  fun providesApplicationContext(): Context {
    return application
  }
}
