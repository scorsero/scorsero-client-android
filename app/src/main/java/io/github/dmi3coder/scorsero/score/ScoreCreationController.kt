package io.github.dmi3coder.scorsero.score

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState.OPEN
import kotlinx.android.synthetic.main.controller_score_creation.view.creation_fab
import kotlinx.android.synthetic.main.controller_score_creation.view.field_list

/**
 * Created by dim3coder on 10:28 PM 7/18/17.
 */
class ScoreCreationController(
    var operationScore: Score = Score()) : Controller(), ScoreCreationContract.View {
  internal lateinit var presenter: ScoreCreationContract.Presenter
  internal lateinit var view: View

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
    view = inflater.inflate(R.layout.controller_score_creation, container, false)
    ScoreCreationPresenter(this).start(operationScore)
    return view
  }

  override fun setPresenter(presenter: Presenter) {
    this.presenter = presenter
  }

  override fun setVisibility(visible: Boolean) {
    TODO("Unsupported")
  }

  override fun setScore(scoreData: Score?) {
    if(scoreData?.id != null){
      view.creation_fab.setImageResource(R.drawable.ic_check)
      activity?.title = "Edit Score"
    } else {
      activity?.title = "New Score"
    }
    operationScore = scoreData ?: Score()
    view.field_list.layoutManager = LinearLayoutManager(activity)
    view.field_list.adapter = ScoreFieldAdapter(operationScore)
    view.creation_fab.setOnClickListener {
      presenter.processScore(operationScore, OPEN)
      router.handleBack()
    }
  }

  override fun clear() {
    setScore(null)
  }
}