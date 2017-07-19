package io.github.dmi3coder.scorsero.score

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter

/**
 * Created by dim3coder on 10:28 PM 7/18/17.
 */
class ScoreCreationController : Controller(), ScoreCreationContract.View {
  internal lateinit var presenter: ScoreCreationContract.Presenter
  internal lateinit var view: View

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    val view = inflater.inflate(R.layout.controller_score_creation, container, false)
    return view
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun setVisibility(visible: Boolean) {
  }

  override fun setScore(scoreData: Score?) {
    TODO("not implemented")
  }

  override fun clear() {
    TODO("not implemented")
  }
}