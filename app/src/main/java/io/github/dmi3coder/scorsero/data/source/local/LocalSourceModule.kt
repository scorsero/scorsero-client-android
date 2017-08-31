package io.github.dmi3coder.scorsero.data.source.local

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by dim3coder on 5:33 PM 8/31/17.
 */
@Module class LocalSourceModule {

  @Provides @Singleton internal fun provideScoreDatabase(context: Context): ScoreDatabase =
      Room.databaseBuilder(context, ScoreDatabase::class.java, "score").build()

  @Provides @Singleton internal fun provideScoreDao(database: ScoreDatabase): ScoreDao =
      database.scoreDao()

  @Provides @Singleton internal fun provideScoreLocalDataSource(
      scoreDao: ScoreDao): ScoreLocalDataSource = ScoreLocalDataSource(scoreDao)

  @Provides @Singleton internal fun provideScoreRepository(
      scoreLocalDataSource: ScoreLocalDataSource) = ScoreRepository(scoreLocalDataSource)
}
