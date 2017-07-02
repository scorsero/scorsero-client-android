package io.github.dmi3coder.scorsero

/**
 * Created by dim3coder on 6:50 PM 7/2/17.
 */
interface BaseContract {

  interface BaseView<in T : BasePresenter> {
    fun setPresenter(presenter: T)
  }

  interface BasePresenter {
    fun prepare()
    fun start()
  }
}