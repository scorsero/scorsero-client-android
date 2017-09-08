package io.github.dmi3coder.scorsero.main

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not

import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toolbar
import com.github.debop.kodatimes.startOfDay
import io.github.dmi3coder.scorsero.MainComponent
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import org.hamcrest.Matcher
import org.joda.time.DateTime
import org.joda.time.Interval
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Answers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.verification.VerificationMode
import java.util.Date

/**
 * Created by dim3coder on 8:48 AM 9/8/17.
 */
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

  private var scoreRepository: ScoreRepository = mock(ScoreRepository::class.java)

  private var mainView: MainContract.View = mock(MainContract.View::class.java)

  @InjectMocks
  private var mainPresenter: MainPresenter = MainPresenter(mainView, elighableIntervals[0], null)


  @Before fun setUp() {
  }

  @Test
  fun loadScoresForDay_loadingIntoView() {
    mainPresenter.start()
    Mockito.verify(mainView).setDate("08 Sep 2017")
    assertThat(mainPresenter.repository, `is`(scoreRepository))
  }

  @After fun tearDown() {}


  companion object {
    var TASKS: List<Score> = listOf(
        Score(1, "Title", "Description", Date().time, false, null),
        Score(2, "Completed", "Description", DateTime().plusDays(-1).toDate().time, true,
            Date().time),
        Score(3, "Title", "Description", Date().time, false, null)
    )
    var elighableIntervals: List<Interval> = listOf(
        Interval(DateTime().startOfDay(), DateTime(2017,9,8,0,0).plusDays(1).startOfDay())
    )
  }
}