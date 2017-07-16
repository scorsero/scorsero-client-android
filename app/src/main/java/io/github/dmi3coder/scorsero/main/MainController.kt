package io.github.dmi3coder.scorsero.main

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.controller_main.view.main_list

/**
 * Created by dim3coder on 6:49 PM 7/2/17.
 */
class MainController : Controller(), MainContract.View {

  var disposal: Disposable? = null
  internal lateinit var view: View
  internal lateinit var presenter: Presenter
  private var scoreAdapter: ScoreAdapter? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_main, container, false)
    return view
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun showScores(scores: Flowable<List<Score>>) {
    disposal = scores.observeOn(AndroidSchedulers.mainThread()).subscribe({
      if (scoreAdapter == null) {
        view.main_list.layoutManager = LinearLayoutManager(activity)
        scoreAdapter = ScoreAdapter(presenter)
        view.main_list.adapter = scoreAdapter
      }
      scoreAdapter!!.setItems(it!!)
    })
  }

  override fun onDestroyView(view: View) {
    super.onDestroyView(view)
    disposal?.dispose()
  }
}
