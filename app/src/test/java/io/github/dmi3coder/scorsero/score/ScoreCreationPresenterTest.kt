package io.github.dmi3coder.scorsero.score

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not

import android.app.Activity
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toolbar
import com.nhaarman.mockito_kotlin.argumentCaptor
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.github.dmi3coder.scorsero.main.time
import io.github.dmi3coder.scorsero.score.ScoreCreationContract.ViewState.OPEN
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by dim3coder on 7:44 PM 9/10/17.
 */
class ScoreCreationPresenterTest {

  private val scoreRepository = mock(ScoreRepository::class.java)

  private val scoreView = mock(ScoreCreationContract.View::class.java)

  @InjectMocks
  private lateinit var scoreCreationPresenter: ScoreCreationPresenter

  @Before fun setUp() {
    scoreCreationPresenter = ScoreCreationPresenter(scoreView)
    MockitoAnnotations.initMocks(this)
  }

  @Test
  fun injectMocks_scoreCreationPresenterInjected() {
    assertThat(scoreCreationPresenter.repository, `is`(equalTo(scoreRepository)))
  }

  @Test
  fun startScoreCreationPresenter_presenterSetToView() {
    scoreCreationPresenter.start()
    verify(scoreView).setPresenter(scoreCreationPresenter)
  }

  @Test
  fun startScoreCreationPresenter_displayingTitle() {
    val emptyScore = Score()
    scoreCreationPresenter.start(emptyScore)
    verify(scoreView).setScore(emptyScore)
  }

  @Test
  fun processScore_scoreInsertedWithCreationDate() {
    val testStartTime = DateTime().time
    val scoreCaptor = argumentCaptor<Score>()
    scoreCreationPresenter.processScore(Score(), OPEN)
    verify(scoreRepository).insert(scoreCaptor.capture())
    assertThat(scoreCaptor.lastValue.creationDate,
        `is`(Matchers.greaterThanOrEqualTo(testStartTime)))
    verify(scoreView).setScore(ArgumentMatchers.any())
  }

  @After fun tearDown() {}
}