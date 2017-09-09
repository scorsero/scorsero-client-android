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
import com.nhaarman.mockito_kotlin.argumentCaptor
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import io.reactivex.Flowable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.notNullValue
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
import org.mockito.ArgumentMatchers
import org.mockito.Captor
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
class MainPresenterTest {

  private var scoreRepository: ScoreRepository = mock(ScoreRepository::class.java)

  private var mainView: MainContract.View = mock(MainContract.View::class.java)

  @InjectMocks
  private lateinit var mainPresenter: MainPresenter


  @Before fun setUp() {
    initPresenterWithInterval(eligibleIntervals[0])
  }

  @Test
  fun injectMocks_mainPresenterInjected() {
    assertThat(mainPresenter.repository, `is`(scoreRepository))
  }

  @Test
  fun startMainPresenter_displayingDate() {
    mainPresenter.start()
    Mockito.verify(mainView).setDate("08 Sep 2016")
  }

  /**
   * Presenter is responsible for zero-time intervals
   * e.g 15:30:50 - 15:30:50
   */
  @Test
  fun startMainPresenterWith0Interval_displayingDate() {
    initPresenterWithInterval(eligibleIntervals[2])
    mainPresenter.start()
    Mockito.verify(mainView).setDate("Today")
  }

  @Test
  fun startMainPresenterWithTodayDate_displayingTodaySign() {
    initPresenterWithInterval(eligibleIntervals[1])
    mainPresenter.start()
    Mockito.verify(mainView).setDate("Today")
  }

  @Test
  fun startMainPresenter_subscribeForDay() {
    eligibleIntervals[0].apply {
      val flowable = Flowable.fromArray(
          TASKS.filter { this.contains(DateTime(it.creationDate!!)) }.toList()
      )
      `when`(scoreRepository.subscribeScoresFor(this)).thenReturn(flowable)
      mainPresenter.start()
      verify(mainView).showScores(flowable)
    }
  }

  @Test
  fun completeScore_scoreCompleted() {
    TASKS[0].completed = false // just to be sure that any other test method haven't changed score
    val scoreCaptor = argumentCaptor<Score>()
    mainPresenter.completeScore(TASKS[0])
    verify(scoreRepository).update(scoreCaptor.capture())
    assertThat(scoreCaptor.firstValue.completed, `is`(true))
    assertThat(scoreCaptor.firstValue.title, `is`("Today Task"))
  }

  @Test
  fun deleteScore_scoreDeleted() {
    val scoreCaptor = argumentCaptor<Score>()
    mainPresenter.removeScore(TASKS[0])
    verify(scoreRepository).delete(scoreCaptor.capture())
    assertThat(scoreCaptor.firstValue.title, `is`(TASKS[0].title))
  }

  @After fun tearDown() {}

  fun initPresenterWithInterval(interval: Interval) {
    mainPresenter = MainPresenter(mainView, interval, null)
    MockitoAnnotations.initMocks(this)
  }

  companion object {
    var TASKS: List<Score> = listOf(
        Score(1, "Today Task", "Description", Date().time, false, null),
        Score(2, "Completed", "Description", DateTime().plusDays(-1).time, true,
            Date().time),
        Score(3, "Title", "Description", Date().time, false, null)
    )
    var eligibleIntervals: List<Interval> = listOf(
        Interval(DateTime(2016, 9, 8, 0, 0).startOfDay(),
            DateTime(2016, 9, 8, 0, 0).plusDays(1).startOfDay()),
        Interval(DateTime().startOfDay(), DateTime().plusDays(1).startOfDay()),
        Interval(DateTime(), DateTime())
    )
  }
}

val DateTime.time: Long
  get() = this.toDate().time