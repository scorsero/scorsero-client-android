package io.github.dmi3coder.scorsero.navigation

import android.graphics.Color
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.github.dmi3coder.scorsero.BaseNavigator
import io.github.dmi3coder.scorsero.BuildConfig
import io.github.dmi3coder.scorsero.MainActivity
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.navigation.NavigationContract.Presenter
import kotlinx.android.synthetic.main.activity_main.drawer_layout
import kotlinx.android.synthetic.main.controller_drawer.view.drawer_list
import kotlinx.android.synthetic.main.controller_drawer.view.stats_chart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.components.XAxis





/**
 * Created by dim3coder on 8:53 AM 8/2/17.
 */
class DrawerController() : Controller(), NavigationContract.View {

  constructor(mainRouter: Router) : this() {
    this.mainRouter = mainRouter
  }

  lateinit var mainRouter: Router
  lateinit internal var presenter: Presenter
  internal var view: View? = null
  lateinit var drawer: DrawerLayout
  private var adapter: DrawerAdapter? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_drawer, container, false)
    drawer = (activity as MainActivity).drawer_layout //The hell I need this complexity?
    val presenter = NavigationPresenter(this, baseNavigator = activity as BaseNavigator)
    presenter.start()
    showSummaryDiagram()
    return view!!
  }

  //TODO: connect repository to diagram
  private fun showSummaryDiagram() {
    var stats_chart = view!!.stats_chart
    stats_chart.apply {
      var dataSet = LineDataSet(
              listOf(
                      Entry(0f, 0f),
                      Entry(1f, 2f),
                      Entry(2f, 1f),
                      Entry(3f, 2f),
                      Entry(4f, 1f),
                      Entry(5f, 2f),
                      Entry(6f, 4f)
              ),"test")
      dataSet.color = Color.YELLOW
      dataSet.setDrawCircles(true)
      dataSet.setDrawValues(true)
      dataSet.lineWidth = 2f
      dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
      this.data = LineData(dataSet)
      this.invalidate()
    }
    val leftAxis = stats_chart.axisLeft
    leftAxis.isEnabled = false
    val rightAxis = stats_chart.axisRight
    rightAxis.isEnabled = false
    val xAxis = stats_chart.xAxis
    xAxis.isEnabled = false
    stats_chart.let {
      it.isHorizontalScrollBarEnabled = false
      it.isVerticalScrollBarEnabled= false
      it.axisLeft.setDrawGridLines(false)
      it.xAxis.setDrawGridLines(false)
      it.setDrawBorders(false)
      it.axisRight.setDrawGridLines(false)
      it.setScaleEnabled(false)
    }

    drawer.addDrawerListener(object : DrawerLayout.DrawerListener{
      override fun onDrawerSlide(drawerView: View?, slideOffset: Float) {

      }

      override fun onDrawerClosed(drawerView: View?) {
        stats_chart.visibility = View.GONE
      }

      override fun onDrawerOpened(drawerView: View?) {
        stats_chart.visibility = View.VISIBLE
        stats_chart.animateXY(300,300)
      }

      override fun onDrawerStateChanged(newState: Int) {
      }

    })



  }

  override fun onRestoreViewState(view: View, savedViewState: Bundle) {
    super.onRestoreViewState(view, savedViewState)
    adapter?.selectSubscription?.onNext(savedViewState.getInt(DRAWER_SELECTED_POSITION))
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun onSaveViewState(view: View, outState: Bundle) {
    super.onSaveViewState(view, outState)
    outState.putInt(DRAWER_SELECTED_POSITION,
        (view.drawer_list.adapter as DrawerAdapter).selectSubscription.value)
  }

  override fun showNavigationItems(items: Array<NavigationItem>) {
    view!!.drawer_list.layoutManager = LinearLayoutManager(activity)
    adapter = DrawerAdapter(items, presenter, drawer)
    view!!.drawer_list.adapter = adapter
  }

  companion object {
    val DRAWER_SELECTED_POSITION = "${BuildConfig.APPLICATION_ID}.navigation.DRAWER_SELECTED_POSITION"
  }
}