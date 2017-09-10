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
import io.github.dmi3coder.scorsero.data.source.ScoreRepository
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
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

  @After fun tearDown() {}
}