package com.example.itaucodechallenge.code.repository.database

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.itaucodechallenge.code.domain.entities.Owner
import com.example.itaucodechallenge.code.domain.entities.Repository
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.StringContains
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@RunWith(AndroidJUnit4::class)
class AppDataBaseTest {

    @get:Rule
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule();

    private val context: Context by lazy {
        ApplicationProvider.getApplicationContext() as Context
    }

    private val testedDAO: DAO by lazy {
        AppDataBase.getInstance().appDAO()
    }

    @Test
    fun when_savingRepository_Should_ReturnRepositoryOrderedByStar() {

        AppDataBase.setupAtApplicationStartup(context)
        runBlocking {
            testedDAO.delteAllRepositories()
        }

        val repositoryList: List<Repository> = Arrays.asList(
                Repository(
                        1,
                        "1",
                        "name2",
                        "description2",
                        222,
                        2,
                        Owner(234, "Login", "http://")
                )
                , Repository(
                2,
                "1",
                "name1",
                "description1",
                111,
                11,
                Owner(123, "Login", "http://")
        )
        )

        runBlocking {
            testedDAO.saveRepositoryList(repositoryList)
        }
        lateinit var restoredRepositoryList: List<Repository>
        lateinit var restoredFirstRepository: Repository
        runBlocking {
            restoredRepositoryList = testedDAO.getRepositoryList("1")
            restoredFirstRepository = restoredRepositoryList.get(0)
        }

        if (restoredRepositoryList != null || restoredFirstRepository != null) {
            assertThat(restoredRepositoryList.size, CoreMatchers.`is`(2))
            assertThat(restoredFirstRepository.name, StringContains("name1"))
            assertThat(restoredFirstRepository.forks, CoreMatchers.`is`(111))
            assertThat(restoredFirstRepository.stargazersCount, CoreMatchers.`is`(11))
            assertThat(restoredFirstRepository.owner.id, CoreMatchers.`is`(123))
        } else {
            error("Error loading from Database")
        }

    }

    @Test
    fun when_ThereIsNoRepository_Should_Return_EmptyList() {
        AppDataBase.setupAtApplicationStartup(context)
        val testedDAO: DAO = testedDAO
        lateinit var restoredRepositoryList: List<Repository>
        runBlocking {
            testedDAO.delteAllRepositories()
            restoredRepositoryList = testedDAO.getRepositoryList("1")
        }

        assertThat(restoredRepositoryList.size, CoreMatchers.`is`(0))

    }

}