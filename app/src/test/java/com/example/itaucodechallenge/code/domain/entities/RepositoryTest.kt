package com.example.itaucodechallenge.code.domain.entities

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class RepositoryTest {

    @Test
    fun should_giveBasicProperties_WhenRepositoryCreatedOnlyWithTitle() {

        var testedRepository: Repository = createRepository(1)
        assertThat(testedRepository.id, `is`(equalTo(1)))
        assertThat(testedRepository.name, `is`(equalTo("price")))
        assertThat(testedRepository.page, `is`(equalTo("http://")))
    }

    fun createRepository(id: Int): Repository {
        return Repository(
                1,
                "http://",
                "price",
                "dolar",
                333,
                2,
                Owner(123,"Login", "http://")
        )
    }
}