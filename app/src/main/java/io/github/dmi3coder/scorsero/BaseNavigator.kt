package io.github.dmi3coder.scorsero

import com.bluelinelabs.conductor.Controller

/**
 * Created by dim3coder on 1:58 PM 8/10/17.
 */
interface BaseNavigator {
  fun showScreen(controller: Controller, addToBackStack: Boolean = false)
}