package com.crowdproj.units.biz.test.repo

import com.crowdproj.units.biz.MkplUnitProcessor
import com.crowdproj.units.common.MkplContext
import com.crowdproj.units.common.MkplCorSettings
import com.crowdproj.units.common.models.*
import com.crowdproj.units.common.repo.DbUnitResponse
import com.crowdproj.units.repo.tests.UnitRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoSuggestTest {

    private val command = MkplCommand.SUGGEST
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = UnitRepositoryMock(
        invokeSuggestUnit = {
            DbUnitResponse(
                isSuccess = true,
                data = MkplUnit(
                    id = MkplUnitId(uuid),
                    name = it.unit.name,
                    description = it.unit.description,
                    alias = it.unit.alias,
                    status = it.unit.status,
                )
            )
        }
    )
    private val settings = MkplCorSettings(
        repoTest = repo
    )
    private val processor = MkplUnitProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoSuggestSuccessTest() = runTest {
        val ctx = MkplContext(
            command = command,
            state = MkplState.NONE,
            workMode = MkplWorkMode.TEST,
            unitRequest = MkplUnit(
                name = "abc",
                description = "abc",
                alias = "ccc",
                status = MkplUnitStatus.SUGGESTED,
            ),
        )
        processor.exec(ctx)
        println(ctx)
        assertEquals(MkplState.FINISHING, ctx.state)
        assertNotEquals(MkplUnitId.NONE, ctx.unitResponse.id)
        assertEquals("abc", ctx.unitResponse.name)
        assertEquals("abc", ctx.unitResponse.description)
        assertEquals("ccc", ctx.unitResponse.alias)
        assertEquals(MkplUnitStatus.SUGGESTED, ctx.unitResponse.status)
    }
}