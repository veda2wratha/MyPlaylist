package com.veda.myplaylist


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PlaylistFeature {

    val activity = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayScreeTitle() {
        assertDisplayed("My Playlist")
    }

    fun displaysListOfPlaylists() {

        assertRecyclerViewItemCount(R.id.playlists_list, 10)

        // TODO Check test cases values
        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_name),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("HArd")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_uName),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 0))
            )
        )
            .check(ViewAssertions.matches(ViewMatchers.withText("rock")))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(
            CoreMatchers.allOf(
                withId(R.id.playlist_image),
                ViewMatchers.isDescendantOfA(nthChildOf(withId(R.id.playlists_list), 1))
            )
        )
            .check(ViewAssertions.matches(withDrawable(R.drawable.ic_launcher_background)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}