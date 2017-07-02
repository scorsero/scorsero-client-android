package io.github.dmi3coder.scorsero.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.main.MainContract.Presenter

/**
 * Created by dim3coder on 6:49 PM 7/2/17.
 */
class MainController : Controller(), MainContract.View {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val view = inflater.inflate(R.layout.controller_main, container, false)

    return view
  }

  override fun setPresenter(presenter: Presenter) {
    TODO("not implemented")
  }


}
