package io.github.dmi3coder.scorsero.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.longClick
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withClassName
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import io.github.dmi3coder.scorsero.MainActivity
import io.github.dmi3coder.scorsero.R
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
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
  public val mActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)
  var controller: MainController? = null

  @Before
  fun setUp() {
    controller = this.mActivityTestRule.activity.router!!.backstack[0].controller() as MainController
  }

  @Test
  fun mainController_onOpen_exist() {
    assertNotNull(controller)
  }

  @Test
  fun mainController_onAddTask_displayed() {
    val testString = "Test text ${Math.random()}"
    createScore(testString)

    val textView = onView(allOf(withId(R.id.title), withText(testString), isDisplayed()))
    textView.check(matches(withText(testString)))
  }

  @Test
  fun mainController_onAddTwoTask_displayed() {
    val stringArray = arrayOf("first score ${Math.random()}", "second score ${Math.random()}")
    stringArray.forEach(this::createScore)
    stringArray.forEach {
      val textView = onView(allOf(withId(R.id.title), withText(it), isDisplayed()))
      textView.check(matches(withText(it)))
    }
  }

  @Test
  fun mainController_onRemoveTask_removed() {
    val testString = "Test text ${Math.random()}"
    createScore(testString)
    val textView = onView(allOf(withId(R.id.title), withText(testString), isDisplayed()))
    textView.check(matches(withText(testString)))
    textView.perform(longClick())
    textView.check(doesNotExist())
  }

  //TODO implement checking over the current screen

  fun createScore(testString: String) {
    val floatingActionButton = onView(allOf(withId(R.id.main_starter_fab),
        childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()))
    floatingActionButton.perform(click())

    val appCompatEditText = onView(withId(R.id.title_field))
    appCompatEditText.perform(replaceText(testString))

    val view = onView(allOf(childAtPosition(allOf(withId(R.id.priority_holder),
        childAtPosition(withClassName(`is`("android.widget.LinearLayout")), 3)), 0), isDisplayed()))
    view.perform(click())

    val floatingActionButton2 = onView(allOf(withId(R.id.main_starter_fab),
        childAtPosition(childAtPosition(withId(android.R.id.content), 0), 2), isDisplayed()))
    floatingActionButton2.perform(click())
  }

  private fun childAtPosition(parentMatcher: Matcher<View>,
      position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent) && view == parent.getChildAt(
            position)
      }
    }
  }
}