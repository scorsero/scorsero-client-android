package io.github.dmi3coder.scorsero.main

import android.arch.lifecycle.LiveData
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainContract.Presenter
import kotlinx.android.synthetic.main.controller_main.view.main_list

/**
 * Created by dim3coder on 6:49 PM 7/2/17.
 */
class MainController : Controller(), MainContract.View {

  internal var view: View? = null

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_main, container, false)
    return view!!
  }

  override fun setPresenter(presenter: Presenter) {
    TODO("not implemented")
  }

  override fun showScores(scores: LiveData<List<Score>>) {
    scores.observeForever {
      view!!.main_list.layoutManager = LinearLayoutManager(activity)
      view!!.main_list.adapter = ScoreAdapter(it!!)
    }
  }


}
