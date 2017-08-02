package io.github.dmi3coder.scorsero

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController
import io.github.dmi3coder.scorsero.navigation.DrawerController
import kotlinx.android.synthetic.main.activity_main.drawer_frame
import kotlinx.android.synthetic.main.activity_main.main_frame
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.joda.time.DateTime

class MainActivity : AppCompatActivity() {

  var router: Router? = null
  var drawerRouter: Router? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupToolbar()
    showRootScreen(savedInstanceState)
  }

  private fun showDrawerRootScreen(savedInstanceState: Bundle?) {
    drawerRouter = Conductor.attachRouter(this, drawer_frame, savedInstanceState)
    if(!drawerRouter!!.hasRootController()) {
      drawerRouter!!.setRoot(RouterTransaction.with(DrawerController()))
    }
  }


  fun showRootScreen(savedInstanceState: Bundle?) {
    router = Conductor.attachRouter(this, main_frame, savedInstanceState)
    if (!router!!.hasRootController()) {
      router!!.setRoot(RouterTransaction.with(MainController(savedInstanceState)))
    }
  }


  private fun setupToolbar() {
    setSupportActionBar(toolbar)
    setToolbarDate(DateTime.now())
  }

  private fun setToolbarDate(date: DateTime) {
    title = date.toString("dd MMM YYYY")
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.score_list, menu)
    return true
  }


  override fun onBackPressed() {
    if (!router!!.handleBack()) {
      super.onBackPressed()
    }
  }
}
