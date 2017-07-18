package io.github.dmi3coder.scorsero

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController
import io.github.dmi3coder.scorsero.main.MainPresenter
import kotlinx.android.synthetic.main.activity_main.main_frame
import kotlinx.android.synthetic.main.activity_main.toolbar
import org.joda.time.DateTime

class MainActivity : AppCompatActivity() {

  var router: Router? = null
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setupToolbar()
    showScreen(savedInstanceState)
  }


  fun showScreen(savedInstanceState: Bundle?) {
    router = Conductor.attachRouter(this, main_frame, savedInstanceState)
    if (!router!!.hasRootController()) {
      val mainController = MainController(savedInstanceState)
      val mainPresenter = MainPresenter(mainController)
      router!!.setRoot(RouterTransaction.with(mainController))
      mainPresenter.start()
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
