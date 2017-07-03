package io.github.dmi3coder.scorsero

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController
import io.github.dmi3coder.scorsero.main.MainPresenter

class MainActivity : AppCompatActivity() {

  var router: Router? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val container = FrameLayout(this)
    setContentView(container)
    router = Conductor.attachRouter(this, container, savedInstanceState)
    if (!router!!.hasRootController()) {
      val mainController = MainController()
      val mainPresenter = MainPresenter(mainController)
      router!!.setRoot(RouterTransaction.with(mainController))
      mainPresenter.start()
    }
  }

  override fun onBackPressed() {
    if (!router!!.handleBack()) {
      super.onBackPressed()
    }
  }
}
