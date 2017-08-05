package io.github.dmi3coder.scorsero.navigation

import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.navigation.NavigationContract.View
import org.joda.time.DateTime
import org.joda.time.Interval

/**
 * Created by dim3coder on 9:38 AM 8/5/17.
 */
class NavigationPresenter(var view: View,
    val repository: ScoreRepository = ScoreRepository.getInstance()) : NavigationContract.Presenter {


  override fun start() {
    var navigationItems: ArrayList<NavigationItem> = ArrayList()
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_today,
            Interval(System.currentTimeMillis(), System.currentTimeMillis()),
            repository.subscribeElementCountFor(DateTime.now(), DateTime.now())
        )
    )
    view.showNavigationItems(navigationItems.toTypedArray())

  }

  override fun navigationChosen(item: NavigationItem) {

  }

  init {
    view.setPresenter(this)
  }
}