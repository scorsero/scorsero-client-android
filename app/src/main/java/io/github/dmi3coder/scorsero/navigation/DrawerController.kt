package io.github.dmi3coder.scorsero.navigation

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.navigation.NavigationContract.Presenter
import kotlinx.android.synthetic.main.controller_drawer.view.drawer_list

/**
 * Created by dim3coder on 8:53 AM 8/2/17.
 */
class DrawerController : Controller(), NavigationContract.View {
  lateinit internal var presenter: Presenter
  internal var view: View? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_drawer, container, false)
    val presenter = NavigationPresenter(this)
    presenter.start()
    return view!!
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun showNavigationItems(items: Array<NavigationItem>) {
    view!!.drawer_list.layoutManager = LinearLayoutManager(activity)
    view!!.drawer_list.adapter = DrawerAdapter(items)
  }
}