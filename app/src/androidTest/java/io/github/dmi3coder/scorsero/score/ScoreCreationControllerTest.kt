package io.github.dmi3coder.scorsero.score

import org.junit.Assert.*
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import android.support.test.runner.lifecycle.Stage.RESUMED
import android.support.test.InstrumentationRegistry.getInstrumentation

import android.app.Activity
import android.support.test.espresso.Espresso.*
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import android.view.KeyEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toolbar
import io.github.dmi3coder.scorsero.MainActivity
import io.github.dmi3coder.scorsero.R
import io.github.dmi3coder.scorsero.R.id
import io.github.dmi3coder.scorsero.data.Score
import io.github.dmi3coder.scorsero.main.MainController
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.joda.time.DateTime
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Created by dim3coder on 2:54 PM 8/18/17.
 */
@RunWith(AndroidJUnit4::class)
class ScoreCreationControllerTest {
  @JvmField
  @Rule val mActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

  @Before fun setUp() {}

  @Test
  fun mainScreen_longTapOnFab_openScoreCreationScreen() {
    onView(withId(R.id.main_starter_fab)).perform(longClick())
    val creationController = getOperationController()
    Assert.assertThat(creationController.javaClass.simpleName,
        `is`(equalTo(ScoreCreationController::class.java.simpleName)))
  }


  @Test
  fun creationController_tapOnFab_CreateScoreAndOpenMainScreen() {
    mainScreen_longTapOnFab_openScoreCreationScreen()
    val controller = getOperationController()
    assertThat(controller.operationScore, `is`(notNullValue()))
    var scoreData = Score()
    scoreData.title = "Creation score ${Math.random()}"
    controller.setScore(scoreData)
    onView(withId(R.id.creation_fab)).perform(click())

    val textView = onView(
        CoreMatchers.allOf(withId(id.title), withText(scoreData.title), isDisplayed()))
    textView.check(ViewAssertions.matches(withText(scoreData.title)))
  }

  @Test
  fun creationController_setScore_uiChanged() {
    mainScreen_longTapOnFab_openScoreCreationScreen()
    val controller = getOperationController()
    assertThat(controller.operationScore, `is`(notNullValue()))
    var scoreData = Score()
    scoreData.title = "Creation score ${Math.random()}"
    scoreData.description = "Creation description ${Math.random()}"
    scoreData.priority = 2
    scoreData.creationDate = DateTime.now().plusDays(1).toDate().time
    scoreData.completed = false
    controller.setScore(scoreData)

    onView(allOf(withId(R.id.field_value), withText(scoreData.title), isDisplayed())).check(ViewAssertions.matches(ViewMatchers.withText(scoreData.title)))
  }


  fun getOperationController(): ScoreCreationController = mActivityTestRule.activity.router!!
      .backstack.last().controller() as ScoreCreationController

  @After fun tearDown() {}
}