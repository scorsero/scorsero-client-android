package io.github.dmi3coder.scorsero.main

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreCreationPresenter
import io.github.dmi3coder.scorsero.score.ScoreStarterController
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.controller_main.view.bottom_sheet_frame
import kotlinx.android.synthetic.main.controller_main.view.main_list
import kotlinx.android.synthetic.main.controller_main.view.main_starter_fab

/**
 * Created by dim3coder on 6:49 PM 7/2/17.
 */
class MainController(val savedInstanceState: Bundle?) : Controller(), MainContract.View {

  var disposal: Disposable? = null
  internal lateinit var view: View
  internal lateinit var presenter: Presenter
  private var scoreAdapter: ScoreAdapter? = null

  var bottomSheetRouter: Router? = null
  var bottomSheetBehavior: BottomSheetBehavior<View>? = null


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_main, container, false)
    setupBottomSheet()
    val linearLayoutManager = LinearLayoutManager(activity)
    linearLayoutManager.reverseLayout = true
    linearLayoutManager.stackFromEnd = true
    view.main_list.layoutManager = linearLayoutManager
    presenter = MainPresenter(this)
    return view
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun onAttach(view: View) {
    super.onAttach(view)
    scoreAdapter = ScoreAdapter(presenter)
    view.main_list.adapter = scoreAdapter
    presenter.start()
  }

  fun setupBottomSheet() {
    bottomSheetBehavior = BottomSheetBehavior.from(view.bottom_sheet_frame)
    bottomSheetBehavior?.isHideable = true
    bottomSheetBehavior?.state = BottomSheetBehavior.STATE_HIDDEN
    val scoreStarterController = ScoreStarterController(bottomSheetBehavior!!)
    val scoreStarterPresenter = ScoreCreationPresenter(scoreStarterController)
    view.main_starter_fab.setOnClickListener(scoreStarterController)
    bottomSheetRouter = Conductor.attachRouter(activity!!, view.bottom_sheet_frame,
        savedInstanceState)
    bottomSheetRouter!!.setRoot(RouterTransaction.with(scoreStarterController))
    scoreStarterPresenter.start()
  }

  override fun showScores(scores: Flowable<List<Score>>) {
    disposal = scores.observeOn(AndroidSchedulers.mainThread()).subscribe({
      scoreAdapter!!.setItems(it!!)
    })
  }

  override fun onDestroyView(view: View) {
    super.onDestroyView(view)
    disposal?.dispose()
  }
}
