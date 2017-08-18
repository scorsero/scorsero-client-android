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
import io.github.dmi3coder.scorsero.main.MainController
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Assert
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
  fun mainScreen_longTapOnFab_openScoreCreationScreen(){
    onView(withId(R.id.main_starter_fab)).perform(longClick())
    val creationController = mActivityTestRule.activity.router!!.backstack.last().controller()
    Assert.assertThat(creationController.javaClass.simpleName, `is`(equalTo(ScoreCreationController::class.java.simpleName)))
  }

  @After fun tearDown() {}
}