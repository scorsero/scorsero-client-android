package io.github.dmi3coder.scorsero.navigation

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import kotlinx.android.synthetic.main.controller_drawer.view.drawer_list

/**
 * Created by dim3coder on 8:53 AM 8/2/17.
 */
class DrawerController : Controller() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val view = inflater.inflate(R.layout.controller_drawer,container,false)
    view.drawer_list.layoutManager = LinearLayoutManager(activity)
    view.drawer_list.adapter = DrawerAdapter()
    return view
  }
}