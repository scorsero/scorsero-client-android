package io.github.dmi3coder.scorsero.navigation

import io.github.dmi3coder.scorsero.BaseNavigator
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.main.MainController
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
            Interval(DateTime(0), DateTime.now())
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_today,
            Interval(DateTime.now(), DateTime.now())
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_tomorrow,
            Interval(DateTime.now().plusDays(1),
                DateTime.now().plusDays(1))
        ))
    navigationItems.add(
        NavigationItem(
            R.string.drawer_options_next_7_days,
            Interval(DateTime.now(), DateTime.now().plusWeeks(1))
        ))
    view.showNavigationItems(navigationItems.toTypedArray())

  }

  override fun navigationChosen(item: NavigationItem) {
    val controller = MainController()
    controller.args.putSerializable("test",item.range)
    baseNavigator.showScreen(controller)
  }

  init {
    view.setPresenter(this)
  }
}