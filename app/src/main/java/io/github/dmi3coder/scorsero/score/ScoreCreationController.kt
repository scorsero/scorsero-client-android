package io.github.dmi3coder.scorsero.score

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.Presenter
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
    val view = inflater.inflate(R.layout.controller_score_creation, container, false)
    view.post {
      view.creation_fab.requestLayout()
    }
    view.field_list.layoutManager = LinearLayoutManager(activity)
    view.field_list.adapter = ScoreFieldAdapter(operationScore)
    view.creation_fab.setOnClickListener {
      val repository = ScoreRepository.getInstance()
      if (operationScore.id != null) {
        repository.update(operationScore)
      } else {
        repository.insert(operationScore)
      }
      router.handleBack()
    }
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