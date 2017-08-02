package io.github.dmi3coder.scorsero.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R

/**
 * Created by dim3coder on 8:53 AM 8/2/17.
 */
class DrawerController : Controller() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val view = inflater.inflate(R.layout.controller_drawer,container,false)

    return view
  }
}