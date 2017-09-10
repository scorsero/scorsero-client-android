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
import io.github.dmi3coder.scorsero.BuildConfig
import io.github.dmi3coder.scorsero.MainApplication
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreCreationController
import io.github.dmi3coder.scorsero.score.ScoreCreationPresenter
import io.github.dmi3coder.scorsero.score.ScoreStarterController
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.controller_main.view.bottom_sheet_frame
import kotlinx.android.synthetic.main.controller_main.view.main_list
import kotlinx.android.synthetic.main.controller_main.view.main_starter_fab
import org.joda.time.Interval

/**
 * Created by dim3coder on 6:49 PM 7/2/17.
 */
class MainController : Controller(), MainContract.View {

  var disposal: Disposable? = null
  internal lateinit var view: View
  internal lateinit var presenter: Presenter
  private var scoreAdapter: ScoreAdapter? = null

  var bottomSheetRouter: Router? = null
  var bottomSheetBehavior: BottomSheetBehavior<View>? = null


  private var interval: Interval? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_main, container, false)
    setupBottomSheet()
    val linearLayoutManager = LinearLayoutManager(activity)
    linearLayoutManager.reverseLayout = true
    linearLayoutManager.stackFromEnd = true
    view.main_list.layoutManager = linearLayoutManager
    if (interval == null) {
      interval = args.getSerializable(CURRENT_DATE_RANGE) as? Interval
    }
    var title: String? = null
    val argTitle = args.getInt(CURRENT_TITLE)
    if (argTitle != 0) {
      title = activity!!.getString(argTitle)
    }
    var mainPresenter = MainPresenter(this, interval!!, title)
    MainApplication.mainComponent.inject(mainPresenter)
    presenter = mainPresenter
    return view
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    interval = savedInstanceState.getSerializable(CURRENT_DATE_RANGE) as Interval
  }

  override fun onRestoreViewState(view: View, savedViewState: Bundle) {
    super.onRestoreViewState(view, savedViewState)
    bottomSheetBehavior?.state = savedViewState.getInt(BOTTOM_SHEET_STATE,
        BottomSheetBehavior.STATE_HIDDEN)
    presenter.restoreTitle(savedViewState.getString(CURRENT_TITLE))
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
    view.main_starter_fab.setOnLongClickListener {
      router.pushController(RouterTransaction.with(ScoreCreationController()))
      true
    }
    bottomSheetRouter = Conductor.attachRouter(activity!!, view.bottom_sheet_frame,
        args)
    bottomSheetRouter!!.setRoot(RouterTransaction.with(scoreStarterController))
    MainApplication.mainComponent.inject(scoreStarterPresenter)
    scoreStarterPresenter.start()
  }

  override fun editScore(score: Score) {
    router.pushController(RouterTransaction.with(ScoreCreationController(score)))
  }

  override fun showScores(scores: Flowable<List<Score>>) {
    disposal = scores.observeOn(AndroidSchedulers.mainThread()).subscribe({
      scoreAdapter!!.setItems(it)
    })
  }

  override fun setDate(date: String?) {
    date?.let {
      activity?.title = date
    }
  }


  override fun onSaveInstanceState(outState: Bundle) {
    outState.putSerializable(CURRENT_DATE_RANGE, interval)
    super.onSaveInstanceState(outState)
  }

  override fun onSaveViewState(view: View, outState: Bundle) {
    outState.putInt(BOTTOM_SHEET_STATE,
        bottomSheetBehavior?.state ?: BottomSheetBehavior.STATE_HIDDEN)
    outState.putString(CURRENT_TITLE, activity?.title.toString())
    super.onSaveViewState(view, outState)
  }

  override fun onDestroyView(view: View) {
    super.onDestroyView(view)
    disposal?.dispose()
  }

  companion object {
    const val BOTTOM_SHEET_STATE = "${BuildConfig.APPLICATION_ID}.main.BOTTOM_SHEET_STATE"
    const val CURRENT_DATE_RANGE = "${BuildConfig.APPLICATION_ID}.main.CURRENT_DATE_RANGE"
    const val CURRENT_TITLE = "${BuildConfig.APPLICATION_ID}.main.CURRENT_TITLE"
  }
}
