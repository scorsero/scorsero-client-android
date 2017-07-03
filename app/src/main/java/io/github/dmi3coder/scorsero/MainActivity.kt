package io.github.dmi3coder.scorsero

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController

class MainActivity : AppCompatActivity() {

  var router: Router? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val container = FrameLayout(this)
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
