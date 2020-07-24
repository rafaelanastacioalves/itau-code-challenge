package com.example.itaucodechallenge.code


import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.itaucodechallenge.code.repository.database.AppDataBase
import com.example.itaucodechallenge.code.ui.repositorieslisting.RepositoryListingActivity
import com.example.itaucodechallenge.code.util.RestServiceTestHelper.getStringFromFile
import com.example.itaucodechallenge.code.util.ViewMatcher
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class RepositoryListingActivityTest {

    @get:Rule
    var repositoryListingActivityTestRule = ActivityTestRule(RepositoryListingActivity::class.java, true, false)
    private val fileNameRepositoryListsPage1OKResponse: String = "repository_list_page_1_response.json"
    private val fileNameRepositoryListsTwoItemsOKResponse = "repository_list_two_items_response.json"
    private var server: MockWebServer? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        server = MockWebServer()
        server!!.start(1234)
        InstrumentationRegistry.registerInstance(InstrumentationRegistry.getInstrumentation(), Bundle())
        server!!.url("/").toString()

        if (InstrumentationRegistry.getInstrumentation().targetContext.databaseList().contains(AppDataBase.databaseName)) {
            if (InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase(AppDataBase.databaseName)) {

            } else {
                error("Database not deleted properly")
            }
        }

    }

    @Test
    fun should_ShowRepositoryListSuccess_WhenAPIIsSuccess() {
        server!!.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromFile(
                        InstrumentationRegistry.getInstrumentation().context
                        , fileNameRepositoryListsPage1OKResponse)
                )
        )
        val intent = Intent()
        repositoryListingActivityTestRule.launchActivity(intent)
        sleep(1000)
        onView(allOf(withId(R.id.repo_text_view_title), withText("CS-Notes"))).check(matches(isDisplayed()))
    }

    @Test
    @Throws(IOException::class)
    fun should_show2Items_WhenAPIGives2Items() {
        server!!.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromFile(
                        InstrumentationRegistry.getInstrumentation().context
                        , fileNameRepositoryListsTwoItemsOKResponse)
                )
        )

        val intent = Intent()
        repositoryListingActivityTestRule.launchActivity(intent)
        sleep(1000)
        val testedPosition = 1
        onView(withId(R.id.repository_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(testedPosition))
        onView(withId(R.id.repository_list)).check(matches(ViewMatcher.showRepositoryItemWithTitle("RxAndroid", testedPosition)))
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        server!!.shutdown()
    }

    @After
    fun deleteDatabase(){
        runBlocking {
            AppDataBase.getInstance()?.appDAO()?.delteAllRepositories()
        }
    }

}
