package io.github.dmi3coder.scorsero

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.main.MainController
import io.github.dmi3coder.scorsero.main.MainPresenter
import io.github.dmi3coder.scorsero.score.ScoreCreationController
import kotlinx.android.synthetic.main.activity_main.bottom_sheet_frame
import kotlinx.android.synthetic.main.activity_main.main_frame
import kotlinx.android.synthetic.main.activity_main.main_starter_fab

class MainActivity : AppCompatActivity() {

  var router: Router? = null
  var bottomSheetRouter: Router? = null
  var bottomSheetBehavior: BottomSheetBehavior<View>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    router = Conductor.attachRouter(this, main_frame, savedInstanceState)

    bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_frame)
    bottomSheetBehavior?.isHideable = true
    val scoreStarterController = ScoreCreationController(bottomSheetBehavior!!)
    main_starter_fab.setOnClickListener(scoreStarterController)
    bottomSheetRouter = Conductor.attachRouter(this, bottom_sheet_frame, savedInstanceState)
    bottomSheetRouter!!.setRoot(RouterTransaction.with(scoreStarterController))
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
