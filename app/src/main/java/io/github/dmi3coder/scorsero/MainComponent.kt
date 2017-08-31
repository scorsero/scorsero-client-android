package io.github.dmi3coder.scorsero

import dagger.Component
import io.github.dmi3coder.scorsero.data.source.local.LocalSourceModule
import io.github.dmi3coder.scorsero.main.MainPresenter
import io.github.dmi3coder.scorsero.navigation.NavigationPresenter
import io.github.dmi3coder.scorsero.score.ScoreCreationPresenter
import javax.inject.Singleton

/**
 * Created by dim3coder on 8:44 PM 8/31/17.
 */
@Singleton
@Component(modules = arrayOf(MainApplicationModule::class, LocalSourceModule::class))
interface MainComponent {
  fun inject(presenter: MainPresenter)
  fun inject(presenter: ScoreCreationPresenter)
  fun inject(navigationPresenter: NavigationPresenter)
}
