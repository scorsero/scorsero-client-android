package io.github.dmi3coder.scorsero.navigation

import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView

/**
 * Created by dim3coder on 9:09 AM 8/5/17.
 */

interface NavigationContract {
  interface Presenter : BasePresenter {
    fun navigationChosen(item: NavigationItem)
  }

  interface View : BaseView<Presenter> {
    fun showNavigationItems(items: Array<NavigationItem>)
  }
}