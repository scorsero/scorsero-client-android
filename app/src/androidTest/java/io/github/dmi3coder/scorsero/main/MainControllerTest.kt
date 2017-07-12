package io.github.dmi3coder.scorsero.main

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import io.github.dmi3coder.scorsero.MainActivity
import junit.framework.TestCase.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by dim3coder on 8:12 AM 7/12/17.
 */
@RunWith(AndroidJUnit4::class)
class MainControllerTest {
  @JvmField
  @Rule
  public val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

  @Test
  fun mainController_onOpen_exist() {
    val controller = activityRule.activity.router!!.backstack[0].controller() as MainController
    assertNotNull(controller)
  }
}