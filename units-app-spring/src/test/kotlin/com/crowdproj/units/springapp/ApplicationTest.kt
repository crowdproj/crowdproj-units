package com.crowdproj.units.springapp

import com.crowdproj.units.repo.gremlin.UnitRepoGremlin
import com.ninjasquad.springmockk.MockkBean
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ApplicationTest {
    @MockkBean
    private lateinit var repo: UnitRepoGremlin

    @Test
    fun contextLoads() {
    }
}