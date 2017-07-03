package io.github.dmi3coder.scorsero

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController

class MainActivity : LifecycleActivity() {

  var router: Router? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    var container = FrameLayout(this)
    setContentView(container)
    router = Conductor.attachRouter(this, container, savedInstanceState)
    if (!router!!.hasRootController()) {
      router!!.setRoot(RouterTransaction.with(MainController()))
    }
  }

  override fun onBackPressed() {
    if (!router!!.handleBack()) {
      super.onBackPressed()
    }
  }
}
