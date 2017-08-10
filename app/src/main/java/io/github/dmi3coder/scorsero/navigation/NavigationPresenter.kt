package io.github.dmi3coder.scorsero.navigation

import io.github.dmi3coder.scorsero.BaseNavigator
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.navigation.NavigationContract.View
import org.joda.time.DateTime
import org.joda.time.Interval

/**
 * Created by dim3coder on 9:38 AM 8/5/17.
 */
class NavigationPresenter(var view: View,
    val repository: ScoreRepository = ScoreRepository.getInstance(),
    val baseNavigator: BaseNavigator) : NavigationContract.Presenter {


  override fun start() {
    var navigationItems: ArrayList<NavigationItem> = ArrayList()
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_todo,
            Interval(null),
            //TODO: add whole subscription
            repository.subscribeElementCountFor(DateTime(0), DateTime.now())
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_today,
            Interval(null),
            repository.subscribeElementCountFor(DateTime.now(), DateTime.now())
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_tomorrow,
            Interval(null),
            repository.subscribeElementCountFor(DateTime.now().plusDays(1),
                DateTime.now().plusDays(1))
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_next_7_days,
            Interval(null),
            repository.subscribeElementCountFor(DateTime.now(), DateTime.now().plusWeeks(1))
        ))
    view.showNavigationItems(navigationItems.toTypedArray())

  }

  override fun navigationChosen(item: NavigationItem) {
    TODO("Implement MainController date choosing")
  }

  init {
    view.setPresenter(this)
  }
}