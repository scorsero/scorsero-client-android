package io.github.dmi3coder.scorsero.main

import io.github.dmi3coder.scorsero.BaseContract.BasePresenter
import io.github.dmi3coder.scorsero.BaseContract.BaseView

/**
 * Created by dim3coder on 6:50 PM 7/2/17.
 */
interface MainContract {

  interface View: BaseView<Presenter>

  interface Presenter: BasePresenter
}